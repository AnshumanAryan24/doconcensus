package com.github.anshumanaryan.votemanager.repository;

import com.github.anshumanaryan.votemanager.model.Motion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotionRepository extends JpaRepository<Motion, Integer> {
}
