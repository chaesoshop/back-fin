package com.carrot.backend.productLike.dao;

import com.carrot.backend.product.domain.Product;
import com.carrot.backend.productLike.domain.ProductLike;
import com.carrot.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductLikeRepository extends JpaRepository <ProductLike,Integer> {
    Optional<ProductLike> findByProductAndUser(Product product, User user);

    void deleteAllByProductProductId(Integer productId);
}
