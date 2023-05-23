package com.carrot.backend.productLike.domain;

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
@Table(name="product_like")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch =FetchType.LAZY)
    private Product product;
    @ManyToOne(fetch =FetchType.LAZY)
    private User user;

    public ProductLike(Product product, User user){
        this.product = product;
        this.user = user;
    }
}
