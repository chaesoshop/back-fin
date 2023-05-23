package com.carrot.backend.reatlyReview.dao;

import com.carrot.backend.reatlyReview.domain.RealtyReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealtyReviewRepository extends JpaRepository<RealtyReview, Integer> {
}
