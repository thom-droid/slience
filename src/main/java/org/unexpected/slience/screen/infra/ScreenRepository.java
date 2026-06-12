package org.unexpected.slience.screen.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.screen.domain.ScreenEntity;

@Repository
public interface ScreenRepository extends JpaRepository<ScreenEntity, Long> {

}