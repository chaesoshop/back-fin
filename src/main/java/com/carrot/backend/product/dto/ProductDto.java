package com.carrot.backend.product.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    Integer productId;

    String productSubject;

    String productContent;

    String productPrice;

    Integer productChatting;

    Integer productView;

    Integer productLike;

    String productDeal;

    String productUserid;

    String productBuyUserid;

    String productCreateTime;

    String profileImage;

    String productCategory;

    List<String> images;

    String productDealAddress;

    boolean reviewFinished;
}
