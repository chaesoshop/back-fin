package com.carrot.backend.productReview.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReviewDto {
    Integer id;

    String productReview;

    Integer productId;

    String buyUserId;

    String sellUserId;

    String reqReviewId;

    String resReviewId;


}
