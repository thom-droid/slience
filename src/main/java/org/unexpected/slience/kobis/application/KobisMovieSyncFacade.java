package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.unexpected.slience.batch.application.BatchCommandService;
import org.unexpected.slience.batch.application.BatchHistoryQueryService;
import org.unexpected.slience.batch.application.exception.BatchFailedException;
import org.unexpected.slience.batch.domain.BatchHistory;
import org.unexpected.slience.batch.domain.BatchType;
import org.unexpected.slience.kobis.api.request.KobisMovieDetailUpdateDto;
import org.unexpected.slience.kobis.api.request.KobisMovieSearchRequest;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.movie.application.MovieCommandService;
import org.unexpected.slience.movie.application.MovieQueryService;
import org.unexpected.slience.schedule.application.ScheduleCommandService;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class KobisMovieSyncFacade {

    private final MovieTempCommandService kobisMovieCommandService;
    private final ScheduleCommandService scheduleCommandService;
    private final BatchHistoryQueryService batchHistoryQueryService;
    private final BatchCommandService batchCommandService;
    private final KobisMovieClient kobisMovieClient;
    private final MovieQueryService movieQueryService;
    private final MovieCommandService movieCommandService;

    public void sync() throws BatchFailedException {

        BatchHistory batchHistory = batchHistoryQueryService.getOrCreate(BatchType.MOVIE.name());
        Long batchId = batchHistory.getBatchId();

        log.info("KobisMovieSyncFacade.sync started");

        try {
            StopWatch stopWatch = new StopWatch("sync");

            stopWatch.start("fetch movies");
            KobisMovieSearchRequest req = KobisMovieSearchRequest.of("2026", "2026", 1, 100);
            KobisMovieListResponse kobisMovieListResponse = kobisMovieClient.fetchMovies(req);
            stopWatch.stop();
            log.info("fetched :: {}, task {} took {} sec", kobisMovieListResponse.getMovieListResult().getMovieList().size(), stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            // 각 독립된 트랜잭션에서 수행하여 트랜잭션 시간을 짧게 부여
            stopWatch.start("insert movie temps");
            int insertedKobisMovieCount = kobisMovieCommandService.insertNewKobisMovie(batchId, kobisMovieListResponse);
            stopWatch.stop();
            log.info("inserted movie temp :: {}. task {} took {} sec", insertedKobisMovieCount, stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            stopWatch.start("insert new items");
            int inserted = kobisMovieCommandService.insertNewMoviesAndDirectors(batchId);
            stopWatch.stop();
            log.info("Inserted new movies and directors for batchId {}. task {} took {} sec", batchId, stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            Set<String> moviesWithoutDetail = movieQueryService.findMoviesWithoutDetail();

            if (moviesWithoutDetail.isEmpty()) {
                batchHistory.completeWithNoUpdate();
                batchCommandService.save(batchHistory);
                log.info("no new movies found. sync finished");
                return;
            }

            stopWatch.start("fetch movie details");
            log.info("start fetching movie detail");
            List<KobisMovieDetailUpdateDto> updatedDetails = moviesWithoutDetail.stream()
                    .map(kobisMovieClient::fetchMovieDetail)
                    .map(KobisAuditChecker::doCheck)
                    .filter(KobisAuditChecker.AuditChecked::valid)
                    .map(KobisMovieDetailUpdateDto::from)
                    .toList();
            stopWatch.stop();
            log.info("task {} took {} sec", stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            stopWatch.start("update movie details");
            int updated = movieCommandService.bulkUpdateMovieDetail(updatedDetails);
            stopWatch.stop();
            log.info("Updated movie details for batchId {}, updated {}. task {} took {} sec", batchId, updated, stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            kobisMovieCommandService.cleanup(batchId);
            scheduleCommandService.syncSchedules();
            log.info("schedule synced");

            batchHistory.complete(inserted, 0);
            batchCommandService.save(batchHistory);
            log.info("KobisMovieSyncFacade.sync finished");

        } catch (Exception e) {
            log.error(e.getMessage());
            batchHistory.fail(e.getMessage());
            batchCommandService.save(batchHistory);
        }
    }
}
