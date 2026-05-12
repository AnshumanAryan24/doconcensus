package com.github.anshumanaryan.votemanager.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StopResponse {
    private final String status;
    private final int successMotions;
    private final int failureMotions;
    private final int totalMotions;
}
