package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.sync.application.SyncHistoryFactory;
import org.unexpected.slience.sync.application.SyncHistoryService;
import org.unexpected.slience.sync.application.exception.SyncFailedException;
import org.unexpected.slience.sync.domain.SyncHistoryEntity;
import org.unexpected.slience.sync.domain.SyncStatus;
import org.unexpected.slience.sync.domain.SyncType;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class KobisMovieSyncFacade {

    private final KobisMovieCommandService kobisMovieCommandService;
    private final SyncHistoryFactory syncHistoryFactory;

    public SyncHistoryEntity sync(KobisMovieListResponse movieListResponse) {
        SyncHistoryService syncHistoryService = syncHistoryFactory.getInstance(SyncType.MOVIE);
        SyncHistoryEntity syncHistoryEntity = null;
        try {
            syncHistoryEntity = kobisMovieCommandService.insertKobisMovie(movieListResponse);
            kobisMovieCommandService.upsertKobisMovie(syncHistoryEntity);
            kobisMovieCommandService.cleanup(syncHistoryEntity);

            // TODO:: domain separation, sync aop style
            syncHistoryEntity.setStatus(SyncStatus.COMPLETED.name());
            syncHistoryEntity.setFinishedAt(LocalDateTime.now());
            syncHistoryService.saveSynHistory(syncHistoryEntity);
            return syncHistoryEntity;

        } catch (Exception e) {
            if (syncHistoryEntity != null && syncHistoryEntity.getBatchId() != null) {
                syncHistoryEntity.setStatus(SyncStatus.FAILED.name());
                syncHistoryEntity.setErrorMessage(e.getMessage());
                syncHistoryService.saveSynHistory(syncHistoryEntity);
                return syncHistoryEntity;
            } else {
              throw new SyncFailedException(e.getMessage());
            }
        }
    }
}
