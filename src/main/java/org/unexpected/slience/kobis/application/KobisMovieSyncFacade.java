package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.unexpected.slience.batch.application.BatchHistoryFactory;
import org.unexpected.slience.batch.application.BatchHistoryService;
import org.unexpected.slience.batch.application.exception.BatchFailedException;
import org.unexpected.slience.batch.domain.BatchHistoryEntity;
import org.unexpected.slience.batch.domain.BatchStatus;
import org.unexpected.slience.batch.domain.BatchType;
import org.unexpected.slience.kobis.api.request.KobisMovieSearchRequest;
import org.unexpected.slience.kobis.api.request.MovieTempUpdateDto;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.schedule.application.ScheduleCommandService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class KobisMovieSyncFacade {

    private final MovieTempCommandService kobisMovieCommandService;
    private final ScheduleCommandService scheduleCommandService;
    private final BatchHistoryFactory syncHistoryFactory;
    private final KobisMovieClient kobisMovieClient;
    private final MovieTempQueryService movieTempQueryService;
    private final MovieTempCommandService movieTempCommandService;

    public void sync() throws BatchFailedException {

        BatchHistoryService batchHistoryService = syncHistoryFactory.getInstance(BatchType.MOVIE);
        BatchHistoryEntity batchHistory = batchHistoryService.getOrCreateBatchHistory();
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
            int insertedKobisMovieCount = kobisMovieCommandService.insertKobisMovie(batchId, kobisMovieListResponse);
            List<String> newMovieCds = movieTempQueryService.findNewMoviesByBatchId(batchId);
            stopWatch.stop();
            log.info("inserted movie temp :: {}. task {} took {} sec", insertedKobisMovieCount, stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            if (newMovieCds.isEmpty()) {
                log.info("no new movies found. sync finished");
                return;
            }

            stopWatch.start("fetch movie details");
            List<MovieTempUpdateDto> bulkUpdates = newMovieCds.stream()
                    .map(kobisMovieClient::fetchMovieDetail)
                    .map(KobisAuditChecker::doCheck)
                    .filter(KobisAuditChecker.AuditChecked::valid)
                    .map(MovieTempUpdateDto::from)
                    .toList();
            stopWatch.stop();
            log.info("task {} took {} sec", stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            stopWatch.start("update movie details");
            int updated = movieTempCommandService.updateMovieDetails(batchId, bulkUpdates);
            stopWatch.stop();
            log.info("Updated movie details for batchId {}, updated {}. task {} took {} sec", batchId, updated, stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            stopWatch.start("upserts");
            int upsert = kobisMovieCommandService.upsertKobisMovie(batchId);
            stopWatch.stop();
            log.info("Upserted movie details for batchId {}. task {} took {} sec", batchId, stopWatch.lastTaskInfo().getTaskName(), stopWatch.lastTaskInfo().getTimeSeconds());

            kobisMovieCommandService.cleanup(batchId);
            scheduleCommandService.syncSchedules();
            log.info("schedule synced");

            batchHistory.setSuccessCount(upsert);
            batchHistory.setStatus(BatchStatus.COMPLETED.name());
            batchHistory.setFinishedAt(LocalDateTime.now());
            batchHistoryService.save(batchHistory);
            log.info("KobisMovieSyncFacade.sync finished");

        } catch (Exception e) {
            batchHistory.setStatus(BatchStatus.FAILED.name());
            batchHistory.setErrorMessage(e.getMessage());
            batchHistoryService.save(batchHistory);
        }
    }
}
