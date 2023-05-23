package com.carrot.backend.product.dao;

import com.carrot.backend.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository <Product, Integer>, CustomizedProductRepository {

    Optional<Product> findByProductId(Integer productId);

    List<Product> findByProductSubject(String keyword);

    List<Product> findByProductSubjectContainingOrProductContentContainingOrProductCategoryContainingOrProductDealAddressContaining(String keyword,String keyword1,String keyword2,String keyword3);

    List<Product> findByProductDealAddressContaining(String keyword);
}
