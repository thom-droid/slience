package org.unexpected.slience.kobis.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.unexpected.slience.movie.infra.DirectorRepository;
import org.unexpected.slience.movie.infra.MovieRepository;
import org.unexpected.slience.kobis.domain.entity.DirectorTempEntity;
import org.unexpected.slience.kobis.domain.entity.MovieTempEntity;
import org.unexpected.slience.kobis.api.response.KobisMovieDto;
import org.unexpected.slience.kobis.api.response.KobisMovieListResponse;
import org.unexpected.slience.kobis.infra.DirectorTempRepository;
import org.unexpected.slience.kobis.infra.MovieTempRepository;
import org.unexpected.slience.movie.mapper.KobisMovieDtoMapper;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieTempCommandService {

    private final MovieTempRepository movieTempRepository;
    private final DirectorTempRepository directorTempRepository;
    private final MovieRepository movieRepository;
    private final DirectorRepository directorRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertNewKobisMovie(Long batchId, KobisMovieListResponse movieListResponse) {

        List<KobisMovieDto> movieList = movieListResponse.getMovieListResult().getMovieList();

        List<DirectorTempEntity> directorTempEntities = new ArrayList<>();
        List<MovieTempEntity> movieTempEntities = movieList
                .stream()
                .map(dto -> {
                    MovieTempEntity movieTempEntity = KobisMovieDtoMapper.mapToEntity(dto);
                    movieTempEntity.setBatchId(batchId);

                    List<DirectorTempEntity> directorTemp = dto.getDirectors().stream()
                            .map(director -> {
                                DirectorTempEntity directorTempEntity = KobisMovieDtoMapper.mapToEntity(director);
                                directorTempEntity.setBatchId(batchId);
                                directorTempEntity.setMovieCd(movieTempEntity.getMovieCd());
                                directorTempEntity.setName(director.getPeopleNm());
                                return directorTempEntity;
                            })
                            .toList();

                    directorTempEntities.addAll(directorTemp);
                    return movieTempEntity;
                })
                .toList();

        List<MovieTempEntity> inserted = movieTempRepository.saveAll(movieTempEntities);
        directorTempRepository.saveAll(directorTempEntities);

        return inserted.size();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insertNewMoviesAndDirectors(Long batchId) {
        int inserted = movieRepository.insertNewMovies(batchId);
        directorRepository.mergeDirector(batchId);
        movieRepository.mergeMovieDirectors(batchId);
        return inserted;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int cleanup(Long batchId) {
        int i = movieTempRepository.deleteByBatchId(batchId);
        directorTempRepository.deleteByBatchId(batchId);
        return i;
    }
}
