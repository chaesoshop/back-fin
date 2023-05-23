package com.carrot.backend.jobsApply.service;

import com.carrot.backend.jobs.dao.JobsRepository;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.jobsApply.dao.JobsApplyRepository;
import com.carrot.backend.jobsApply.domain.JobsApply;
import com.carrot.backend.jobsApply.dto.JobsApplyDto;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobsApplyService {
    private final JobsApplyRepository jobsApplyRepository;
    private final JobsRepository jobsRepository;

    private final UserRepository userRepository;

    public boolean apply(Integer jobsId, JobsApplyDto applyJobsDto) {
        try {
            JobsApply jobsApply = new JobsApply();
            Jobs jobs = jobsRepository.findById(jobsId).orElseThrow();
            User user = userRepository.findByUserid(applyJobsDto.getUserid()).get();
            jobs.setJobVolunteer(jobs.getJobVolunteer()+1);
            jobsRepository.save(jobs);
            jobsApply.setJobs(jobs);
            jobsApply.setImgURL(user.getProfileImage());
            jobsApply.setUserid(applyJobsDto.getUserid());
            jobsApply.setName(applyJobsDto.getName());
            jobsApply.setGender(applyJobsDto.getGender());
            jobsApply.setPhoneValue(applyJobsDto.getPhoneValue());
            jobsApply.setIntroduce(applyJobsDto.getIntroduce());
            jobsApply.setYear(applyJobsDto.getYear());
            LocalDateTime date = LocalDateTime.now();
            String dates = date.toString();
            String yymmdd = dates.substring(0, 10);
            jobsApply.setApplyDate(yymmdd);
            jobsApplyRepository.save(jobsApply);
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public List<JobsApply> getJobApply(Jobs jobs) {
        List<JobsApply> jobsApplies = jobsApplyRepository.findAllByJobs(jobs);
        return jobsApplies;
    }

    public boolean isExist(Integer num, String userid) {
        Jobs jobs = jobsRepository.findById(num).get();
        Optional<JobsApply> jobsApply = jobsApplyRepository.findByJobsAndUserid(jobs,userid);
        if(jobsApply.isPresent()){
            return true;
        }
        return false;
    }
}
