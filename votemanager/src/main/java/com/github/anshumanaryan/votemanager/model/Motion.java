package com.github.anshumanaryan.votemanager.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Motion {
    @RequiredArgsConstructor
    public static enum MotionStages {
        PROPOSED("proposed"),
        IN_VOTE("in_vote"),
        VOTED_SUCCESS("success"),
        VOTED_FAILED("failed");

        @Getter
        private final String value;
    }
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Setter
    @Getter
    private int motionId;
    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private MotionStages stage = MotionStages.PROPOSED;
    @Setter
    @Getter()
    private int section;
    @Setter
    @Getter
    private String changeText;
    @Setter
    @Getter
    private int votesInFavour;
    @Setter
    @Getter
    private int votesAgainst;
}