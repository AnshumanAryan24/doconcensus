package com.github.anshumanaryan.votemanager.repository;

import com.github.anshumanaryan.votemanager.model.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotionRepository extends JpaRepository<Motion, Integer> {

    @Query("select m.stage from Motion m where m.motionId = :motionId")
    Motion.MotionStages getLatestStage(@Param("motionId") Integer motionId);

    @Modifying
    @Query("update Motion m set m.stage = :stage where m.motionId = :motionId")
    void setStage(@Param("motionId") Integer motionId, @Param("stage") Motion.MotionStages stage);

    @Query("select m from Motion m where m.stage = :value")
    List<Motion> findMotionsByStage(Motion.MotionStages value);

    @Modifying
    @Query("update Motion m set m.votesInFavour = m.votesInFavour + 1 where m.motionId = :motionId")
    void IncrementVotesInFavour(int motionId);

    @Modifying
    @Query("update Motion m set m.votesAgainst = m.votesAgainst + 1 where m.motionId = :motionId")
    void IncrementVotesAgainst(int motionId);
}
