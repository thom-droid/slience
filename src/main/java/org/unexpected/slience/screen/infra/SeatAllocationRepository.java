package org.unexpected.slience.screen.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.reservation.domain.entity.SeatAllocationEntity;

@Repository
public interface SeatAllocationRepository extends JpaRepository<SeatAllocationEntity, Long> {

}
