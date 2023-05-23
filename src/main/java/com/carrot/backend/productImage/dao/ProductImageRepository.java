package com.carrot.backend.productImage.dao;

import com.carrot.backend.productImage.domain.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository <ProductImages,Long> {

    List<ProductImages> findAllByProductProductId(Integer productId);
}
