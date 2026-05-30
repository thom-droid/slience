package org.unexpected.slience.movie.api.response;

import org.unexpected.slience.movie.domain.entity.DirectorEntity;

public record MovieDirectorResponse(String directorName){

    public static MovieDirectorResponse from(DirectorEntity directorEntity) {
        return new MovieDirectorResponse(directorEntity.getName());
    }

}