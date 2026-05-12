package com.github.anshumanaryan.votemanager.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StartRequest {
    private final int documentId;
    private final int totalMembers;
}
