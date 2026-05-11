package com.github.anshumanaryan.votemanager.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoteResponse {
    @Getter
    private final int motionId;
    @Getter
    private final boolean votedBefore;
}
