package com.github.anshumanaryan.votemanager.controller;

import com.github.anshumanaryan.votemanager.dto.request.VoteRequest;
import com.github.anshumanaryan.votemanager.dto.response.VoteResponse;
import com.github.anshumanaryan.votemanager.model.Motion;
import com.github.anshumanaryan.votemanager.service.MotionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/api/motions")
@RequiredArgsConstructor
public class MotionController {

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
