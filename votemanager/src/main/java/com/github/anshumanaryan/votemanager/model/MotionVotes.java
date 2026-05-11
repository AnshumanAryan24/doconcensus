package com.github.anshumanaryan.votemanager.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class MotionVotes {
    @Id
    private int motionId;

    @ElementCollection // Will store the set in a separate table automatically
    private final Set<Integer> votedMembers = new HashSet<>();

    public MotionVotes(int motionId) {
        this.motionId = motionId;
    }

    public boolean voterIsEligible(int memberId) {
        return !votedMembers.contains(memberId);
    }

    public void addVotedMember(int memberId) {
        votedMembers.add(memberId);
    }
}
