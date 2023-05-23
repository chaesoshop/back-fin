package com.carrot.backend.jobImage.dao;

import com.carrot.backend.jobImage.domain.JobsImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobsImageRepository extends JpaRepository<JobsImages, Integer> {


    List<JobsImages> findAllByJobsJobid(Integer jobsId);
}
