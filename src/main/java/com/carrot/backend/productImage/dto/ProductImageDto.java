package com.carrot.backend.productImage.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ProductImageDto {
    Integer imageId;

    String path;

    Integer productId;

    public ProductImageDto(Integer productId, String path) {
        this.path = path;
        this.productId = productId;
    }
}
