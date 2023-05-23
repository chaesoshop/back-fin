package com.carrot.backend.jobs.controller;

import com.carrot.backend.jobImage.service.JobsImageService;
import com.carrot.backend.jobLike.service.JobsLikeService;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.jobsApply.dto.JobsApplyDto;
import com.carrot.backend.jobs.dto.JobsDto;
import com.carrot.backend.jobs.service.JobsService;
import com.carrot.backend.jobsApply.service.JobsApplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class JobsController {

    private final JobsService jobsService;
    private final JobsImageService jobsImageService;
    private final JobsLikeService jobsLikeService;

    private final JobsApplyService jobsApplyService;
    @GetMapping("/jobs")
    public List<Jobs> getJobs(){
        return jobsService.getJobs();
    }

    @GetMapping("/Jobs/{jobsId}")
    public Jobs getJob(@PathVariable Integer jobsId){
        return jobsService.getJob(jobsId);
    }

    @PostMapping("/createJobs")
    public Jobs createJobs(@RequestBody JobsDto jobsDto){
        Integer id = jobsService.createJobs(jobsDto);
        System.out.println("job id : " + id);
        return jobsService.getJob(id);
    }
    @PostMapping("/createJobsImages")
    public Jobs createJobsImg(@RequestPart(value = "jobDto") JobsDto jobsDto, @RequestPart("file") List<MultipartFile> multipartFile) throws IOException{
        Integer id = jobsService.createJobs(jobsDto);
        jobsImageService.uploads(id, multipartFile, "jobsImages");
        return jobsService.getJob(id);
    }

    //조회수 올리는 메소드
    @PostMapping("/jobsCheck/{jobsId}")
    public void jobsCheck(@PathVariable Integer jobsId) {
        jobsService.jobCheck(jobsId);
    }

    @GetMapping("/likeJob/{jobId}")
    public boolean likeJob(@RequestParam Integer jobId,@RequestParam String userid){
        return jobsLikeService.addJobsLike(jobId,userid);
    }

    @GetMapping("/likeJobsCheck/{jobsId}")
    public boolean isLikedJobs(@RequestParam Integer jobsId,@RequestParam  String userid){
        return jobsLikeService.checkLikeJobs(jobsId, userid);
    }

    @GetMapping("/getJobsWithImage/{jobsId}")
    public JobsDto getJobsDto(@PathVariable Integer jobsId){

        return jobsService.getJobsWithImage(jobsId);
    }


    @PostMapping("/jobsDelete/{jobsId}")
    public void jobsDelete(@PathVariable Integer jobsId) {
        jobsImageService.jobsDelete(jobsId, "jobsImages");
    }
    @PostMapping("/applyJobs/{jobsId}")
    public boolean applyJobs(@PathVariable Integer jobsId, @RequestBody JobsApplyDto applyJobsDto){
        boolean rs = jobsApplyService.apply(jobsId, applyJobsDto);

        return rs;
    }
    @GetMapping("/jobssearch/{search}")
    public List<Jobs> jobsSearch(@PathVariable String search){
        List<Jobs> jobs = jobsService.jobSearch(search);
        return jobs;
    }

    @PostMapping("/jobsEdit/{jobsId}")
    public Jobs jobsEdit(@PathVariable Integer jobsId, @RequestBody JobsDto jobsDto){
        return  jobsService.setJobs(jobsId, jobsDto);
    }

    @GetMapping("/hotJobs")
    public List<Jobs> hotJobs(){
        return jobsService.getHotJobs();
    }

}

