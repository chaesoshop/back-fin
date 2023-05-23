package com.carrot.backend.reatlyReview.domain;

import com.carrot.backend.realty.domain.Realty;
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
@Table(name="realty_review")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RealtyReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch =FetchType.LAZY)
    private Realty realty;

    @ManyToOne(fetch =FetchType.LAZY)
    private User buyUser;

    @ManyToOne(fetch =FetchType.LAZY)
    private User sellUser;

    @Column
    private String realtyReview;
    @ManyToOne(fetch =FetchType.LAZY)
    private User reqReview;

    @ManyToOne(fetch =FetchType.LAZY)
    private User resReview;


}
