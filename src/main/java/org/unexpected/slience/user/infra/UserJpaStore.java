package org.unexpected.slience.user.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.unexpected.slience.user.application.exception.NoUserFoundException;
import org.unexpected.slience.user.domain.User;
import org.unexpected.slience.user.domain.UserEntity;
import org.unexpected.slience.user.domain.UserEntityMapper;

@RequiredArgsConstructor
@Repository
public class UserJpaStore implements UserStore {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository
                        .findById(id)
                        .map(UserEntityMapper::toUserDomain)
                        .orElseThrow(() -> new NoUserFoundException("User not found: " + id));
    }

    @Override
    public User save(User user) {
        UserEntity save = userRepository.save(UserEntityMapper.toEntity(user));
        return UserEntityMapper.toUserDomain(save);
    }
}
