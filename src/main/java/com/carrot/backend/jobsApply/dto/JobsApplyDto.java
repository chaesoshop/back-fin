package com.carrot.backend.jobsApply.dto;

import com.carrot.backend.jobs.domain.Jobs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobsApplyDto {
    Integer id;

    Jobs jobsId;

    String name;

    String phoneValue;

    String gender;

    int year;

    String introduce;

    String applyDate;

    String userid;

    String imgURL;
}
