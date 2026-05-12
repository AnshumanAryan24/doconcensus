package com.github.anshumanaryan.votemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VotemanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VotemanagerApplication.class, args);
    }

}
