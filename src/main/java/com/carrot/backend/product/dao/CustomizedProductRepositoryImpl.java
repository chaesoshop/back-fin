package com.carrot.backend.product.dao;

import com.carrot.backend.product.domain.Product;
import com.carrot.backend.product.domain.QProduct;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.productImage.domain.QProductImages;
import com.carrot.backend.productLike.domain.QProductLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.carrot.backend.product.domain.QProduct.product;

@RequiredArgsConstructor
public class CustomizedProductRepositoryImpl implements CustomizedProductRepository{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Product getQslProduct(Integer id) {
        return jpaQueryFactory.select(product)
                .from(product)
                .where(product.productId.eq(1))
                .fetchOne();
    }

    @Override
    public ProductDto getQslProductsAndImagesByProductId(Integer productId){

        Product product = jpaQueryFactory
                .select(QProduct.product)
                .from(QProduct.product)
                .innerJoin(QProductImages.productImages)
                .on(QProduct.product.productId.eq(productId))
                .fetchOne();

        List<String> imagePaths = new ArrayList<>();
        product.getImages().stream().forEach(productImage->imagePaths.add(productImage.getPath()));

        ProductDto productDto = ProductDto.builder()
                .productId(product.getProductId())
                .productCategory(product.getProductCategory())
                .productView(product.getProductView())
                .productContent(product.getProductContent())
                .productSubject(product.getProductSubject())
                .productCreateTime(product.getProductCreateTime())
                .productChatting(product.getProductChatting())
                .productLike(product.getProductLike())
                .productPrice(product.getProductPrice())
                .productDealAddress(product.getProductDealAddress())
                .productUserid(product.getProductUserid())
                .productBuyUserid(product.getProductBuyUserid())
                .productDeal(product.getProductDeal())
                .reviewFinished(product.isReviewFinished())
                .images(imagePaths)
                .build();

        return productDto;
    }

    public List<ProductDto> getQslProductsBySearch(String search){

//        List<Product> products = jpaQueryFactory
//                .select(QProduct.product)
//                .from(QProduct.product)
//                .innerJoin(QProductImages.productImages)
//                .on(QProduct.product.productId.eq(productId))
//                .fetchOne();

        return null;
    }

    @Override
    @Transactional
    public void deleteQslProductAndLikeByProductId(Integer productId) {
        Long product1 = jpaQueryFactory
                .delete(QProductLike.productLike)
                .where(QProductLike.productLike.product.productId.eq(productId))
                .execute();
        Long product = jpaQueryFactory
                .delete(QProduct.product)
                .where(QProduct.product.productId.eq(productId))
                .execute();
    }

}
