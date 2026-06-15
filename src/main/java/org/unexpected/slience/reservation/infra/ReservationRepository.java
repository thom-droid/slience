package org.unexpected.slience.reservation.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.reservation.domain.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

}