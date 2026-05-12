package com.github.anshumanaryan.votemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCounterServiceImpl implements CounterService {
    private final StringRedisTemplate redisTemplate;
    private final String SUCCESS_MOTIONS_KEY = "counter:success";
    private final String FAILURE_MOTIONS_KEY = "counter:failure";
    private final String TOTAL_MOTIONS_KEY = "counter:total";

    @Override
    public void incrementSuccessMotions() {
        this.redisTemplate.opsForValue().increment(this.SUCCESS_MOTIONS_KEY);
        this.redisTemplate.opsForValue().increment(this.TOTAL_MOTIONS_KEY);
    }

    @Override
    public void incrementFailureMotions() {
        this.redisTemplate.opsForValue().increment(this.FAILURE_MOTIONS_KEY);
        this.redisTemplate.opsForValue().increment(this.TOTAL_MOTIONS_KEY);
    }

    @Override
    public int getSuccess() {
        return Integer.parseInt(this.redisTemplate.opsForValue().get(this.SUCCESS_MOTIONS_KEY));
    }

    @Override
    public int getFailure() {
        return Integer.parseInt(this.redisTemplate.opsForValue().get(this.FAILURE_MOTIONS_KEY));
    }

    @Override
    public int getTotal() {
        return Integer.parseInt(this.redisTemplate.opsForValue().get(this.TOTAL_MOTIONS_KEY));
    }
}
