package org.unexpected.slience.reservation.api;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConcurrentReservationTest {

    @LocalServerPort
    int port;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void onlyOneRequestSuccessWhileOthersFailing() throws Exception {
        String queryForData = """
                SELECT sch.id  AS schedule_id, 
                        m.id AS movie_id,
                        sch.screen_id AS screen_id,
                        st.id AS seat_id,
                        ss.id AS schedule_seat_id
                FROM schedule_seats ss 
                JOIN schedules sch ON sch.id = ss.schedule_id
                JOIN movies m ON m.id = sch.movie_id
                JOIN seats st ON st.id = ss.seat_id
                LEFT JOIN seat_allocations sa ON sa.schedule_seat_id = ss.id
                WHERE sa.schedule_seat_id IS NULL 
                AND m.status = 'PLAYING'
                LIMIT 1
                """;

        Map<String, @Nullable Object> map = jdbcTemplate.queryForMap(queryForData);

        long scheduleId = ((Number) map.get("schedule_id")).longValue();
        long movieId = ((Number) map.get("movie_id")).longValue();
        long seatIds = ((Number) map.get("seat_id")).longValue();
        long screenId = ((Number) map.get("screen_id")).longValue();
        long scheduleSeatId = ((Number) map.get("schedule_seat_id")).longValue();

        String body = """
                    {
                    "scheduleId": %d,
                    "movieId": %d,
                    "seatIds": [%d],
                    "screenId": %d
                    }
                """.formatted(scheduleId, movieId, seatIds, screenId);

        int requestCount = 30;
        ExecutorService executorService = Executors.newFixedThreadPool(requestCount);

        CountDownLatch ready = new CountDownLatch(requestCount);
        CountDownLatch start = new CountDownLatch(1);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:" + port + "/api/v1/reservations");

        List<? extends Future<?>> futures = IntStream.range(0, requestCount)
                .mapToObj(i -> executorService.submit(() -> {
                    ready.countDown();
                    try {
                        start.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    HttpRequest request = HttpRequest.newBuilder(uri)
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(body))
                            .build();

                    try {
                        return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).statusCode();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }))
                .toList();

        ready.await();
        start.countDown();

        List<Integer> statuses = new ArrayList<>();
        for (Future<?> future : futures) {
            statuses.add((Integer) future.get());
        }

        executorService.shutdown();

        long createdCount = statuses.stream().filter(s -> s == 201).count();

        String allocationQuery = """
                    SELECT COUNT(*)
                    FROM seat_allocations 
                    WHERE schedule_seat_id = ?
                """;

        Integer allocationCount = jdbcTemplate.queryForObject(allocationQuery, Integer.class, scheduleSeatId);

        assertThat(createdCount).isEqualTo(1);
        assertThat(allocationCount).isEqualTo(1);

    }
}