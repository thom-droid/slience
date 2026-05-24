package org.unexpected.slience.kobis.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unexpected.slience.kobis.api.request.KobisMovieSearchRequest;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.kobis.application.KobisMovieClient;
import org.unexpected.slience.kobis.application.KobisMovieSyncFacade;
import org.unexpected.slience.sync.domain.SyncHistoryEntity;

@RequestMapping("/ext/api/kobis")
@RequiredArgsConstructor
@RestController
public class KobisMovieSyncController {

    private final KobisMovieClient kobisMovieClient;
    private final KobisMovieSyncFacade kobisMovieSyncFacade;

    @GetMapping("/sync")
    public void sync() {

        KobisMovieSearchRequest req = KobisMovieSearchRequest.of("2026", "2026", 1, 100);
        KobisMovieListResponse kobisMovieListResponse = kobisMovieClient.fetchMovies(req);
        System.out.println("kobisMovieListResponse = " + kobisMovieListResponse);

        SyncHistoryEntity sync = kobisMovieSyncFacade.sync(kobisMovieListResponse);
        System.out.println("sync = " + sync);
    }

}
