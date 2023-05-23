package com.carrot.backend.realtyLike.dao;

import com.carrot.backend.jobLike.domain.JobsLike;
import com.carrot.backend.jobs.domain.Jobs;
import com.carrot.backend.realty.domain.Realty;
import com.carrot.backend.realtyLike.domain.RealtyLike;
import com.carrot.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RealtyLikeRepository extends JpaRepository <RealtyLike, Integer> {
    Optional<RealtyLike> findByRealtyAndUser(Realty realty, User user);
}
