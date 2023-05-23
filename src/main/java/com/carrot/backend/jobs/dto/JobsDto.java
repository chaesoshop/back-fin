package com.carrot.backend.jobs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobsDto {
    Integer jobid;

    String jobSubject;

    String jobUserid;

    String createDate;

    private String jobName;

    private String jobCategory;

    private String jobPrice;

    String jobPlace;

    String jobDay;

    private String jobStartTime;

    private String jobEndTime;

    String jobContent;

    Integer jobVolunteer;

    Integer jobLike;

    Integer jobCheck;

    String profileImage;

    List<String> images;

}
