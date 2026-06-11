package org.unexpected.slience.user.infra;

import org.unexpected.slience.user.domain.User;

public interface UserStore {
    User findById(Long id);
    User save(User user);
}
