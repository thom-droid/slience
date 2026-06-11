package org.unexpected.slience.user.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unexpected.slience.user.domain.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}