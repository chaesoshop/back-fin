package com.carrot.backend.product.domain;

import com.carrot.backend.productImage.domain.ProductImages;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer productId;
    @Column(length = 40)
    @NotEmpty
    String productSubject;
    @Column(length = 200)
    @NotEmpty
    String productContent;
    @Column
    @NotEmpty
    String productPrice;
    @Column
    Integer productChatting;
    @Column
    Integer productView;
    @Column
    Integer productLike;
    @Column
    String productUserid;
    @Column
    String productBuyUserid;
    @Column
    String profileImage;

    String productCreateTime;
    @Column
    @NotEmpty
    String productCategory;

    @Column
    String productDeal;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<ProductImages> images;

    @Column
    String productDealAddress;

    @Column
    private boolean reviewFinished;

}
