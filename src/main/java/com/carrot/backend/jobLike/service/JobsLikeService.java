package com.carrot.backend.jobLike.service;

import com.carrot.backend.jobLike.dao.JobsLikeRepository;
import com.carrot.backend.jobLike.domain.JobsLike;
import com.carrot.backend.jobs.dao.JobsRepository;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class JobsLikeService {
    private final JobsLikeRepository jobsLikeRepository;
    private final UserRepository userRepository;
    private final JobsRepository jobsRepository;

    public boolean addJobsLike(Integer jobsid, String userid){
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Jobs jobs = jobsRepository.findByJobid(jobsid).orElseThrow(()-> new DataNotFoundException("product not found"));
        if(isNotAlreadyLikeJobs(jobs,user)){
            jobsLikeRepository.save(new JobsLike(jobs, user));
            int prev = jobs.getJobLike();
            jobs.setJobLike(prev+1);
            return true;
        }else{
            JobsLike like = jobsLikeRepository.findByJobsAndUser(jobs,user).orElseThrow(()-> new DataNotFoundException("like not found"));
            int prev = jobs.getJobLike();
            jobs.setJobLike(prev-1);
            jobsLikeRepository.delete(like);
            return false;
        }
    }
    private boolean isNotAlreadyLikeJobs(Jobs jobs, User user) {
        return jobsLikeRepository.findByJobsAndUser(jobs, user).isEmpty();
    }

    public boolean checkLikeJobs(Integer jobsId, String userid) {
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Jobs jobs = jobsRepository.findByJobid(jobsId).orElseThrow(()-> new DataNotFoundException("jobs not found"));
        if(isNotAlreadyLikeJobs(jobs,user)){

            return false;
        }else{

            return true;
        }
    }
}

