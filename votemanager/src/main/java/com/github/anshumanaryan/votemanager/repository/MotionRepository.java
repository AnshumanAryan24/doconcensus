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
    String getLatestStage(@Param("motionId") Integer motionId);

    @Modifying
    @Query("update Motion m set m.stage = ':stageString' where m.motionId = :motionId")
    void setStage(@Param("motionId") Integer motionId, @Param("stage") String stage);

    @Query("select m.motionId, m.stage, m.section, m.changeText, m.proposingMemberId, m.votesInFavour, m.votesAgainst from Motion m where m.stage = ':value'")
    List<Motion> findMotionsByStage(String value);
}
