package com.carrot.backend.jobsApply.controller;

import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.jobs.service.JobsService;
import com.carrot.backend.jobsApply.domain.JobsApply;
import com.carrot.backend.jobsApply.service.JobsApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JobApplyController {

    private final JobsApplyService jobsApplyService;
    private final JobsService jobsService;

    @GetMapping("/getJobApply")
    public List<JobsApply> getJobsApply (@RequestParam("num") Integer num){

        Jobs jobs = jobsService.getJob(num);

        List<JobsApply> jobsApplies = jobsApplyService.getJobApply(jobs);

        return jobsApplies;
    }

    @GetMapping("/checkApply/{num}")
    public boolean checkApply (@PathVariable Integer num, @RequestParam("userid")String userid){
        boolean rs = jobsApplyService.isExist(num,userid);
        return rs;
    }

}
