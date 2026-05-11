package com.github.anshumanaryan.votemanager.service;

import com.github.anshumanaryan.votemanager.model.Motion;
import com.github.anshumanaryan.votemanager.model.MotionVotes;
import com.github.anshumanaryan.votemanager.repository.MotionRepository;
import com.github.anshumanaryan.votemanager.repository.MotionVotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MotionService {

    private final MotionRepository motionRepository;
    private final MotionVotesRepository motionVotesRepository;
    private MotionVotes voteIdempotencyChecker;

    public Optional<Motion> getMotionByMotionId(int motionId) {
        return this.motionRepository.findById(motionId);
    }

    public Motion saveMotion(Motion motion) {
        Motion gotMotion = this.motionRepository.save(motion);
        this.voteIdempotencyChecker = this.motionVotesRepository.save(new MotionVotes(gotMotion.getProposingMemberId()));
        this.voteIdempotencyChecker.addVotedMember(gotMotion.getProposingMemberId());
        return gotMotion;
    }

    @Transactional
    public Motion updateMotion(int motionId, Motion motion) {
        Motion gotMotion = this.motionRepository
                .findById(motionId)
                .orElseThrow(() ->
                        new RuntimeException(
                                String.format("Motion with id %d not found", motionId)
                        )
                );

        gotMotion.setSection(motion.getSection());
        gotMotion.setChangeText(motion.getChangeText());
        gotMotion.setVotesInFavour(motion.getVotesInFavour());
        gotMotion.setVotesAgainst(motion.getVotesAgainst());

        return this.motionRepository.save(gotMotion);
    }

    @Transactional
    public boolean voteIfNotVotedBefore(int motionId, int memberId, boolean voteInFavour) {
        // Returns whether member has voted before or not
        if (!this.voteIdempotencyChecker.voterIsEligible(memberId)) {
            // throw new RuntimeException("Member " + memberId + " has already voted on motion " + motionId);
            return true;
        }

        Motion motion = this.motionRepository.findById(motionId)
                .orElseThrow(() -> new RuntimeException("Motion not found"));

        if (voteInFavour) {
            motion.setVotesInFavour(motion.getVotesInFavour() + 1);
        } else {
            motion.setVotesAgainst(motion.getVotesAgainst() + 1);
        }

        this.voteIdempotencyChecker.addVotedMember(memberId);

        return false;
    }

}
