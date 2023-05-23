package com.carrot.backend.jobLike.dao;

import com.carrot.backend.jobLike.domain.JobsLike;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobsLikeRepository extends JpaRepository <JobsLike, Integer > {
    Optional<JobsLike> findByJobsAndUser(Jobs jobs, User user);
}
