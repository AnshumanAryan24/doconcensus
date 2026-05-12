package com.github.anshumanaryan.votemanager.service;

import com.github.anshumanaryan.votemanager.model.Motion;
import com.github.anshumanaryan.votemanager.model.MotionVotes;
import com.github.anshumanaryan.votemanager.repository.MotionRepository;
import com.github.anshumanaryan.votemanager.repository.MotionVotesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        this.motionVotesRepository.save(this.voteIdempotencyChecker);
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

        if (motionRepository.getLatestStage(motionId)
                .equals(Motion.MotionStages.PROPOSED)) {
            motionRepository.setStage(motionId, Motion.MotionStages.IN_VOTE);
        }
        if (!votingOpen(motion)) {
            return true;
        }

        if (voteInFavour) {
            motionRepository.IncrementVotesInFavour(motionId);
        } else {
            motionRepository.IncrementVotesAgainst(motionId);
        }

        this.voteIdempotencyChecker.addVotedMember(memberId);
        this.motionVotesRepository.save(this.voteIdempotencyChecker);

        return false;
    }

    @Transactional
    public void closeVoting(int motionId, Motion.MotionStages result) {
        motionRepository.setStage(motionId, result);
    }

    private boolean votingOpen(Motion motion) {
        return motionRepository.getLatestStage(motion.getMotionId())
                .equals(Motion.MotionStages.IN_VOTE);
    }

    public List<Motion> getMotionsInVote() {
        return this.motionRepository.findMotionsByStage(Motion.MotionStages.IN_VOTE);
    }
}
