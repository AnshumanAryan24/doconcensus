package com.github.anshumanaryan.votemanager.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoteRequest {
    @Getter
    private final int memberId;
    @Getter
    private final boolean voteInFavour;
    @Getter
    private final int motionId;
}
