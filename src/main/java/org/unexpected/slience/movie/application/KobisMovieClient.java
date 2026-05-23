package org.unexpected.slience.movie.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.unexpected.slience.config.kobis.KobisProperties;
import org.unexpected.slience.movie.dto.kobis.KobisMovieListResponse;

@Component
@RequiredArgsConstructor
public class KobisMovieClient {

    private final WebClient webClient;

    private final KobisProperties kobisProperties;

    public KobisMovieListResponse searchMovies(String movieName) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("kobis.or.kr")
                        .path("/kobisopenapi/webservice/rest/movie/searchMovieList.json")
                        .queryParam("key", kobisProperties.getApi().getKey())
                        .build())
                .retrieve()
                .bodyToMono(KobisMovieListResponse.class)
                .block();
    }
}