package org.unexpected.slience.screen.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.movie.domain.Movie;
import org.unexpected.slience.screen.domain.ScreenEntity;

import java.util.List;

@Repository
public interface ScreenRepository extends JpaRepository<ScreenEntity, Long> {
    
}