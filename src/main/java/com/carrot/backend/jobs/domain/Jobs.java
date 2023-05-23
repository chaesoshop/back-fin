package com.carrot.backend.jobs.domain;

import com.carrot.backend.jobImage.domain.JobsImages;
import com.carrot.backend.jobsApply.domain.JobsApply;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Jobs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer jobid;

    @Column(length = 50)
    @NotEmpty
     String jobSubject;

    @Column
     String jobUserid;

    String createDate;

    private String jobName;

    @Column
    private String jobCategory;

    @Column
    @NotEmpty
    private String jobPrice;

    @Column
     String jobPlace;

    @Column
     String jobDay;

    @Column
    private String jobStartTime;

    @Column
    private String jobEndTime;

    @Column(length = 1000)
    @NotEmpty
     String jobContent;

    @Column
     Integer jobVolunteer;

    @Column
     Integer jobLike;

    @Column
     Integer jobCheck;

    @Column
    String profileImage;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<JobsImages> images;

    @OneToMany(mappedBy = "jobs", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<JobsApply> applyJobs;
}
