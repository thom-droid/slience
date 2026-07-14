package org.unexpected.slience;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.unexpected.slience.kobis.application.KobisMovieSyncFacade;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartupRunner {

    private final KobisMovieSyncFacade kobisMovieSyncFacade;
    private final Environment env;

    // 스프링 준비되면 동기화 실시
    @EventListener(ApplicationReadyEvent.class)
    public void initMovie() {
        String syncMovies = System.getProperty("syncMovies");
        if ("Y".equalsIgnoreCase(syncMovies)) {
            log.info("list={}", env.getProperty("kobis.api.path.list"));
            log.info("detail={}", env.getProperty("kobis.api.path.detail"));
            kobisMovieSyncFacade.sync();
        }
    }
}
