package com.carrot.backend.jobsApply.domain;


import com.carrot.backend.jobs.domain.Jobs;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "jobs_apply")
public class JobsApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String name;

    @Column
    String phoneValue;

    @Column
    String gender;

    @Column
    int year;

    @Column
    String introduce;

    String applyDate;

    String imgURL;

    String userid;

    @ManyToOne
    private Jobs jobs;
}
