package com.carrot.backend.productImage.controller;


import com.carrot.backend.product.Service.ProductService;
import com.carrot.backend.productImage.Service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductImageController {
    private final ProductService productService;
    private final ProductImageService productImageService;
}

