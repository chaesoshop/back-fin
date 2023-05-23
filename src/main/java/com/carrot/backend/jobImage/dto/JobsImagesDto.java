package com.carrot.backend.jobImage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobsImagesDto {
    Long jobImageId;

    String jobPath;

    Integer jobId;

    public JobsImagesDto(Integer jobId, String jobPath) {
        this.jobPath = jobPath;
        this.jobId = jobId;
    }
}
