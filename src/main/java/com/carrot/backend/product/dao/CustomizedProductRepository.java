package com.carrot.backend.product.dao;

import com.carrot.backend.product.domain.Product;
import com.carrot.backend.product.dto.ProductDto;

import java.util.List;


public interface CustomizedProductRepository {


    Product getQslProduct(Integer id);

    ProductDto getQslProductsAndImagesByProductId(Integer productId);

    void deleteQslProductAndLikeByProductId(Integer productId);

    List<ProductDto> getQslProductsBySearch(String search);


}

