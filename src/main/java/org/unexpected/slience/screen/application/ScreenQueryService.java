package org.unexpected.slience.screen.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.unexpected.slience.screen.infra.ScreenRepository;

@RequiredArgsConstructor
@Service
public class ScreenQueryService {

    private final ScreenRepository screenRepository;


}
