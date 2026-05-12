package com.github.anshumanaryan.votemanager.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RedisCounterServiceImpl implements CounterService {
    private final StringRedisTemplate redisTemplate;
    private final String SUCCESS_MOTIONS_KEY = "counter:success";
    private final String FAILURE_MOTIONS_KEY = "counter:failure";
    private final String TOTAL_MOTIONS_KEY = "counter:total";

    public RedisCounterServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
        this.registerKeys();
    }

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
        return Integer.parseInt(Objects.requireNonNull(this.redisTemplate.opsForValue().get(this.SUCCESS_MOTIONS_KEY)));
    }

    @Override
    public int getFailure() {
        return Integer.parseInt(Objects.requireNonNull(this.redisTemplate.opsForValue().get(this.FAILURE_MOTIONS_KEY)));
    }

    @Override
    public int getTotal() {
        return Integer.parseInt(Objects.requireNonNull(this.redisTemplate.opsForValue().get(this.TOTAL_MOTIONS_KEY)));
    }

    public void registerKeys() {
        this.redisTemplate.opsForValue().set(this.SUCCESS_MOTIONS_KEY, "0");
        this.redisTemplate.opsForValue().set(this.FAILURE_MOTIONS_KEY, "0");
        this.redisTemplate.opsForValue().set(this.TOTAL_MOTIONS_KEY, "0");
    }
}
