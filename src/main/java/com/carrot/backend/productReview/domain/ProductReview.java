package com.carrot.backend.productReview.domain;

import com.carrot.backend.product.domain.Product;
import com.carrot.backend.user.domain.User;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product_review")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch =FetchType.LAZY)
    private Product product;

    @ManyToOne(fetch =FetchType.LAZY)
    private User buyUser;

    @ManyToOne(fetch =FetchType.LAZY)
    private User sellUser;

    @Column
    private String productReview;

    @ManyToOne(fetch =FetchType.LAZY)
    private User reqReview;

    @ManyToOne(fetch =FetchType.LAZY)
    private User resReview;



}
