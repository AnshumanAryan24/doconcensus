package com.github.anshumanaryan.votemanager.controller;

import com.github.anshumanaryan.votemanager.dto.request.StartRequest;
import com.github.anshumanaryan.votemanager.dto.request.VoteRequest;
import com.github.anshumanaryan.votemanager.dto.response.StopResponse;
import com.github.anshumanaryan.votemanager.dto.response.VoteResponse;
import com.github.anshumanaryan.votemanager.model.Motion;
import com.github.anshumanaryan.votemanager.service.MotionService;
import com.github.anshumanaryan.votemanager.service.ProcedureStatisticsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/api/motions")
@RequiredArgsConstructor
public class MotionController {

    private final ProcedureStatisticsService procedureStatisticsService;

    private final StringRedisTemplate redisTemplate;
    private final String DOCUMENT_ID_KEY = "documentId";
    private final String TOTAL_MEMBERS_KEY = "totalMembers";

    private enum DefaultVotes {
        DEFAULT_IN_FAVOUR(1),
        DEFAULT_AGAINST(0);

        @Getter
        private final int voteCount;

        DefaultVotes(int voteCount) {
            this.voteCount = voteCount;
        }
    }

    private final MotionService motionService;

    @PostMapping("/start")
    public @ResponseBody Map<String, String> start(@RequestBody StartRequest startRequest) {
        this.redisTemplate.opsForValue().set(
                this.DOCUMENT_ID_KEY, String.valueOf(startRequest.getDocumentId())
        );
        this.redisTemplate.opsForValue().set(
                this.TOTAL_MEMBERS_KEY, String.valueOf(startRequest.getTotalMembers())
        );
        return Collections.singletonMap(
                "status", "success"
        );
    }

    @PostMapping("/stop")
    public @ResponseBody StopResponse stop() {
        return procedureStatisticsService.getStopResponse();
    }

    @PostMapping("/propose")
    public Motion propose(@RequestBody Motion motion) {
        motion.setVotesInFavour(DefaultVotes.DEFAULT_IN_FAVOUR.getVoteCount());
        motion.setVotesAgainst(DefaultVotes.DEFAULT_AGAINST.getVoteCount());
        motion.setStage(Motion.MotionStages.PROPOSED);
        return motionService.saveMotion(motion);
    }

    @GetMapping("/motion")
    public Motion getMotionById(@RequestParam Integer id) {
        return motionService.getMotionByMotionId(id).orElse(null);
    }

    @PostMapping("/vote")
    public @ResponseBody VoteResponse vote(@RequestBody VoteRequest voteRequest) {
        boolean votedBefore = this.motionService.voteIfNotVotedBefore(
                voteRequest.getMotionId(), voteRequest.getMemberId(), voteRequest.isVoteInFavour()
        );
        return new VoteResponse(voteRequest.getMotionId(), votedBefore);
    }
}
