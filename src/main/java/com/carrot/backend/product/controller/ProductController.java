package com.carrot.backend.product.controller;

import com.carrot.backend.product.Service.ProductService;
import com.carrot.backend.product.domain.Product;
import com.carrot.backend.product.dto.ProductDto;
import com.carrot.backend.productImage.Service.ProductImageService;
import com.carrot.backend.productLike.service.ProductLikeService;
import com.carrot.backend.productReview.domain.ProductReview;
import com.carrot.backend.productReview.dto.ProductReviewDto;
import com.carrot.backend.productReview.service.ProductReviewService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductLikeService productLikeService;
    private final ProductImageService productImageService;

    private final ProductReviewService productReviewService;

    @GetMapping("")
    public List<Product> getProducts(){
        return productService.getProducts();
    }
    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable Integer productId){

        return productService.getProduct(productId);
    }

    @PostMapping("/createProduct")
    public Product createProduct(@RequestBody ProductDto productDto){
        Integer id = productService.createProduct(productDto);

        return productService.getProduct(id);
    }
    @PostMapping("/createProductImages")
    public Product createProductWithImages(@RequestPart(value = "productDto") ProductDto productDto, @RequestPart("file") List<MultipartFile> multipartFile) throws IOException {

        Integer id = productService.createProduct(productDto);
        productImageService.uploads(id, multipartFile, "images");

        return productService.getProduct(id);
    }

    @GetMapping("/likeProduct/{productId}")
    public boolean likeProduct(@RequestParam Integer productId,@RequestParam  String userid){
        return productLikeService.addProductLike(productId,userid);
    }
    @GetMapping("/likeProductCheck/{productId}")
    public boolean isLiked(@RequestParam Integer productId,@RequestParam  String userid){
        return productLikeService.checkLike(productId, userid);
    }

    @GetMapping("/getProductWithImage/{productId}")
    public ProductDto getPI(@PathVariable Integer productId){

        return productService.getProductWithImage(productId);
    }

    @GetMapping("/getProductByDongGu")
    public List<Product> getDongGu(){
        return productService.getProductDongGu();
    }

    @PostMapping("/productView/{productId}")
    public void productView(@PathVariable Integer productId){
        productService.View(productId);
        productService._productView(productId);
    }

    @PostMapping("/productDelete/{productId}")
    public void productDelete(@PathVariable Integer productId){
        productImageService.delete(productId, "images");
    }

    @PostMapping("/productEdit/{productId}")
    public Product productEdit(@PathVariable Integer productId, @RequestBody ProductDto productDto){
        return productService.setProduct(productId, productDto);
    }

    @PostMapping("/productImageEdit/{productId}")
    public void productImageEdit(@PathVariable Integer productId,@RequestPart(value = "productDto") ProductDto productDto, @RequestPart("file") List<MultipartFile> multipartFile) throws IOException {
        productService.setProduct(productId, productDto);
        productImageService.deleteImage(productId,"images");
        productImageService.uploads(productId, multipartFile, "images");
    }

    @GetMapping("/product/search")
    public List<ProductDto> search(@RequestParam(value = "keyword", defaultValue = "") String keyword, Model model){
        model.addAttribute("keyword", keyword);
        System.out.println("1번" + keyword);

        return productService.searchProduct(keyword);
    }
    @PostMapping("/productReview")
    public ProductReview productReview(@RequestBody ProductReviewDto productReviewDto){
        return productReviewService.addReview(productReviewDto);
    }

    @PostMapping("/productBuyReview")
    public ProductReview productBuyReview(@RequestBody ProductReviewDto productReviewDto){
        return productReviewService.addBuyReview(productReviewDto);
    }

    @GetMapping("/hotProduct")
    public List<Product> hotProduct(){
        return productService.hotProduct();
    }



}
