package com.carrot.backend.jobImage.domain;

import com.carrot.backend.jobs.domain.Jobs;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "job_image")
public class JobsImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long jobImageId;

    String jobPath;

    @ManyToOne
    private Jobs jobs;
}
