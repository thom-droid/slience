package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.unexpected.slience.config.kobis.KobisProperties;
import org.unexpected.slience.kobis.api.request.KobisMovieSearchRequest;
import org.unexpected.slience.kobis.api.response.KobisMovieDto;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.kobis.api.response.KobisMovieListResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class KobisMovieClient {

    private final WebClient webClient;

    private final KobisProperties kobisProperties;

    // TODO:: 대용량 처리 (~ 100k)
    public KobisMovieListResponse fetchMovies(KobisMovieSearchRequest request) {
        MultiValueMap<String, String> multiValueMap = KobisMovieSearchRequest.toMultiValueMap(request);
        KobisMovieListResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("kobis.or.kr")
                        .path("/kobisopenapi/webservice/rest/movie/searchMovieList.json")
                        .queryParam("key", kobisProperties.getApi().getKey())
                        .queryParams(multiValueMap)
                        .build())
                .retrieve()
                .bodyToMono(KobisMovieListResponse.class)
                .block();

        if (response != null && response.getMovieListResult() != null) {
            KobisMovieListResult movieListResult = response.getMovieListResult();
            List<KobisMovieDto> filtered = movieListResult.getMovieList().stream().filter(m -> !KobisAdultGenreFilter.shouldFilter(m.getRepGenreNm())).toList();
            movieListResult.setMovieList(filtered);
            movieListResult.setTotCnt(filtered.size());
        }

        return response;
    }
}