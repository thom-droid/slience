package org.unexpected.slience.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.user.infra.UserStore;

@RequiredArgsConstructor
@Service
public class UserQueryService {

    private final UserStore userJpaStore;




}
