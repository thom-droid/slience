package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.unexpected.slience.config.kobis.KobisProperties;
import org.unexpected.slience.kobis.api.request.KobisMovieSearchRequest;
import org.unexpected.slience.kobis.api.response.KobisMovieDetailDto;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;

@Component
@RequiredArgsConstructor
public class KobisMovieClient {
    private final WebClient webClient;
    private final KobisProperties kobisProperties;

    // TODO:: 대용량 처리 (~ 100k)
    public KobisMovieListResponse fetchMovies(KobisMovieSearchRequest request) {
        MultiValueMap<String, String> multiValueMap = KobisMovieSearchRequest.toMultiValueMap(request);
        KobisProperties.Api api = kobisProperties.api();
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(api.scheme())
                        .host(api.host())
                        .path(api.path().list())
                        .queryParam("key", api.key())
                        .queryParams(multiValueMap)
                        .build())
                .retrieve()
                .bodyToMono(KobisMovieListResponse.class)
                .block();
    }

    public KobisMovieDetailDto fetchMovieDetail(String movieCd) {
        KobisProperties.Api api = kobisProperties.api();
        KobisMovieDetailDto block = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme(api.scheme())
                        .host(api.host())
                        .path(api.path().detail())
                        .queryParam("key", api.key())
                        .queryParam("movieCd", movieCd)
                        .build())
                .retrieve()
                .bodyToMono(KobisMovieDetailDto.class)
                .block();
        return block;
    }
}