package com.github.anshumanaryan.votemanager.service;

import com.github.anshumanaryan.votemanager.model.Motion;
import com.github.anshumanaryan.votemanager.repository.MotionRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MotionService {

    private final MotionRepository motionRepository;

    public Optional<Motion> getMotionByMotionId(int motionId) {
        return this.motionRepository.findById(motionId);
    }

    public Motion saveMotion(Motion motion) {
        return this.motionRepository.save(motion);
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
}
