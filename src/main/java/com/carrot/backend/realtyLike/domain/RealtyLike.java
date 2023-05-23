package com.carrot.backend.realtyLike.domain;

import com.carrot.backend.jobs.domain.Jobs;
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
@Table(name="realty_like")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class RealtyLike {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        @ManyToOne(fetch = FetchType.LAZY)
        private Realty realty;
        @ManyToOne(fetch = FetchType.LAZY)
        private User user;

        public RealtyLike(Realty realty, User user) {
            this.realty = realty;
            this.user = user;
        }
}
