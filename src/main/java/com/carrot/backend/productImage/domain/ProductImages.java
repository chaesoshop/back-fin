package com.carrot.backend.productImage.domain;

import com.carrot.backend.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="product_image")
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long imageId;

    String path;

    @ManyToOne
    @JsonIgnore
    private Product product;


}
