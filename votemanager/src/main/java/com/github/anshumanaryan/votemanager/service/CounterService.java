package com.github.anshumanaryan.votemanager.service;

public interface CounterService {
    public void incrementSuccessMotions();

    public void incrementFailureMotions();

    public int getSuccess();

    public int getFailure();

    public int getTotal();
}


