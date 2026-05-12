package com.github.anshumanaryan.votemanager.service.component;

import com.github.anshumanaryan.votemanager.model.Motion;
import com.github.anshumanaryan.votemanager.service.MotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VotingJudge {
    private final MotionService motionService;
    private final float SIMPLE_MAJORITY = 0.51f;

    private final StringRedisTemplate redisTemplate;
    private final String TOTAL_MEMBERS_KEY = "totalMembers";

    @Scheduled(fixedRate = 5000)
    public void checkAndAttemptConclude() {
        int totalMembers;
        try {
            String stringTotalMembers = redisTemplate.opsForValue().get(TOTAL_MEMBERS_KEY);
            if (stringTotalMembers == null) {
                return;
            } else {
                totalMembers = Integer.parseInt(stringTotalMembers);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }


        List<Motion> motions = this.motionService.getMotionsInVote();
        for (Motion motion : motions) {
            int inFavour = motion.getVotesInFavour();
            int against = motion.getVotesAgainst();

            if (1.0f * inFavour / totalMembers >= this.SIMPLE_MAJORITY) {
                this.motionService.closeVoting(motion, Motion.MotionStages.VOTED_SUCCESS);
            } else if (1.0f * against / totalMembers >= this.SIMPLE_MAJORITY) {
                this.motionService.closeVoting(motion, Motion.MotionStages.VOTED_FAILED);
            }
        }
    }
}
