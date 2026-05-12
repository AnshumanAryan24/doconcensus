package com.github.anshumanaryan.votemanager.service;

import com.github.anshumanaryan.votemanager.dto.response.StopResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcedureStatisticsService {
    CounterService counterService;

    public void incrementSuccess() {
        this.counterService.incrementSuccessMotions();
    }

    public void incrementFailure() {
        this.counterService.incrementFailureMotions();
    }

    public StopResponse getStopResponse() {
        return new StopResponse(
                "ok",
                this.counterService.getSuccess(),
                this.counterService.getFailure(),
                this.counterService.getTotal()
        );
    }
}
