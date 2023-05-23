package com.carrot.backend.product.Service;

import com.carrot.backend.product.dao.CustomizedProductRepositoryImpl;
import com.carrot.backend.product.dao.ProductRepository;
import com.carrot.backend.product.domain.Product;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.productImage.dao.ProductImageRepository;
import com.carrot.backend.productImage.domain.ProductImages;
import com.carrot.backend.util.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    private final CustomizedProductRepositoryImpl customizedProductRepository;

    public Product getProduct(Integer productId) {
        try {
            Optional<Product> product = this.productRepository.findByProductId(productId);
            if (product.isPresent()) {
                return product.get();
            }
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ProductDto getProductWithImage(Integer productId) {
        ProductDto product = customizedProductRepository.getQslProductsAndImagesByProductId(productId);
        return product;
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Integer createProduct(ProductDto productDto) {
        Product newProduct = new Product();

        newProduct.setProductPrice(productDto.getProductPrice());
        newProduct.setProductSubject(productDto.getProductSubject());
        newProduct.setProductContent(productDto.getProductContent());
        newProduct.setProductCategory(productDto.getProductCategory());
        newProduct.setProductChatting(0);
        newProduct.setProductLike(0);
        newProduct.setProductView(0);
        newProduct.setProductDeal("판매중");
        newProduct.setProductUserid(productDto.getProductUserid());
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0, 10);
        System.out.println(yymmdd);
        newProduct.setProductCreateTime(yymmdd);
        newProduct.setProductDealAddress(productDto.getProductDealAddress());
        newProduct.setReviewFinished(false);
        productRepository.save(newProduct);
        return newProduct.getProductId();
    }

    public void View(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("not found"));
        product.setProductView(product.getProductView() + 1);
    }

    public void _productView(Integer productId) {
        Product product = productRepository.findByProductId(productId).orElseThrow(() -> new DataNotFoundException("product not found"));
        product.setProductView(product.getProductView() + 1);
        productRepository.save(product);
    }

    public Product setProduct(Integer productId,ProductDto productDto) {
        Product product = productRepository.findByProductId(productId).orElseThrow(()-> new DataNotFoundException("product not found"));

        product.setProductPrice(productDto.getProductPrice());
        product.setProductSubject(productDto.getProductSubject());
        product.setProductContent(productDto.getProductContent());
        product.setProductCategory(productDto.getProductCategory());
        product.setProductDealAddress(productDto.getProductDealAddress());
        LocalDateTime date = LocalDateTime.now();
        String dates = date.toString();
        String yymmdd = dates.substring(0, 10);
        System.out.println(yymmdd);
        product.setProductCreateTime(yymmdd);

        return productRepository.save(product);
    }

    public void setProductImage(Integer productId, ProductDto productDto) {
        List<ProductImages> product = productImageRepository.findAllByProductProductId(productId);


    }


    public List<ProductDto> searchProduct(String keyword) {
        List<Product> products = productRepository.findByProductSubject(keyword);
        List<ProductDto> productDtoList = new ArrayList<>();
        if(products.isEmpty()){
            return productDtoList;
        }
        for(Product product : products) {
            productDtoList.add(this.searchDto(product));
        }
        return productDtoList;
    }

    private ProductDto searchDto(Product product){
        return  ProductDto.builder()
                .productId(product.getProductId())
                .productCategory(product.getProductCategory())
                .productContent(product.getProductContent())
                .productSubject(product.getProductSubject())
                .productDealAddress(product.getProductDealAddress())
                .productUserid(product.getProductUserid())
                .build();
    }

    public void addChatNumProduct(Integer articleId) {
        Product product = productRepository.findByProductId(articleId).get();
        product.setProductChatting(product.getProductChatting()+1);
        productRepository.save(product);

    }

    public List<Product> getProductDongGu() {
        return productRepository.findByProductDealAddressContaining("동구");
    }
    public List<Product> hotProduct(){
       return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productView","productCreateTime"));

    }
//
//    public ProductDto setProductWithImage(Integer productId) {
//        ProductDto product = customizedProductRepository.setQslProductsAndImagesByProductId(productId);
//        return product;
//    }

}
