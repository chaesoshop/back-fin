package com.carrot.backend.jobs.dao;

import com.carrot.backend.jobs.dto.JobsDto;

import javax.transaction.Transactional;

public interface CustomizedJobsRepository {



    JobsDto getQslJobsAndImagesByJobId(Integer jobsId);

    @Transactional
    void deleteQslJobsAndImagesByJobId(Integer jobsId);
}
