package org.unexpected.slience.view;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.unexpected.slience.movie.api.response.MovieListResponse;
import org.unexpected.slience.movie.api.response.MovieScheduleResponse;
import org.unexpected.slience.movie.application.MovieQueryService;
import org.unexpected.slience.movie.domain.entity.Status;
import org.unexpected.slience.reservation.api.response.CreateReservationResponse;
import org.unexpected.slience.reservation.application.ReservationCommandService;
import org.unexpected.slience.reservation.domain.ReservationMapper;
import org.unexpected.slience.schedule.application.ScheduleQueryService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PageController {

    private static final DateTimeFormatter REQUEST_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    private final MovieQueryService movieQueryService;
    private final ScheduleQueryService scheduleQueryService;
    private final ReservationCommandService reservationCommandService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/movies")
    public String movies(@RequestParam(value = "status", required = false) Status status,
                         @RequestParam(value = "date", required = false) String date,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         Model model) {
        List<MovieListResponse> movies = movieQueryService.getMovies(status, date, page)
                .stream()
                .map(MovieListResponse::from)
                .toList();

        model.addAttribute("movies", movies);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("selectedStatus", status);
        model.addAttribute("date", date);
        model.addAttribute("page", page);
        return "movies/list";
    }

    @GetMapping("/movies/playing")
    public String playing(@RequestParam(value = "movieId", required = false) Long movieId,
                          @RequestParam(value = "date", required = false) String date,
                          Model model) {
        String selectedDate = date == null || date.isBlank()
                ? LocalDate.now().format(REQUEST_DATE)
                : date;

        List<MovieScheduleResponse> movies = movieQueryService.getMovieSchedulesByDate(movieId, selectedDate);

        model.addAttribute("movies", movies);
        model.addAttribute("movieId", movieId);
        model.addAttribute("date", selectedDate);
        return "movies/playing";
    }

    @GetMapping("/movies/{id}")
    public String movieDetail(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieQueryService.getMovieDetail(id));
        return "movies/detail";
    }

    @GetMapping("/schedules/{id}")
    public String scheduleDetail(@PathVariable Long id, Model model) {
        model.addAttribute("scheduleDetail", scheduleQueryService.getScheduleDetail(id));
        return "schedules/detail";
    }

    @GetMapping("/reservations/new")
    public String reservationForm(@RequestParam(value = "movieId", required = false) Long movieId,
                                  @RequestParam(value = "scheduleId", required = false) Long scheduleId,
                                  Model model) {
        ReservationForm form = new ReservationForm();
        form.setMovieId(movieId);
        form.setScheduleId(scheduleId);
        model.addAttribute("reservationForm", form);
        return "reservations/new";
    }

    @PostMapping("/reservations")
    public String createReservation(@ModelAttribute ReservationForm reservationForm, Model model) {
        CreateReservationResponse reservation = ReservationMapper.toCreateReservationResponse(
                reservationCommandService.reserve(
                        reservationForm.getMovieId(),
                        reservationForm.getScheduleId(),
                        reservationForm.seatIdList()));

        model.addAttribute("reservation", reservation);
        return "reservations/detail";
    }

    @Getter
    @Setter
    public static class ReservationForm {
        private Long movieId;
        private Long scheduleId;
        private String seatIds;

        public List<Long> seatIdList() {
            if (seatIds == null || seatIds.isBlank()) {
                return List.of();
            }
            return Arrays.stream(seatIds.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isBlank())
                    .map(Long::valueOf)
                    .toList();
        }
    }
}
