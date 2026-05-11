package com.github.anshumanaryan.votemanager.repository;

import com.github.anshumanaryan.votemanager.model.MotionVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionVotesRepository extends JpaRepository<MotionVotes, Integer> {
}
