package com.carrot.backend.productReview.dao;

import com.carrot.backend.productReview.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
}
