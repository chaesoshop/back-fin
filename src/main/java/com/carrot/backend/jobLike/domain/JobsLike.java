package com.carrot.backend.jobLike.domain;

import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="jobs_like")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JobsLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Jobs jobs;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public JobsLike(Jobs jobs, User user) {
        this.jobs = jobs;
        this.user = user;
    }
}