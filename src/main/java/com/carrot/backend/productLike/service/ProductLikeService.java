package com.carrot.backend.productLike.service;

import com.carrot.backend.product.dao.ProductRepository;
import com.carrot.backend.product.domain.Product;
import com.carrot.backend.productLike.dao.ProductLikeRepository;
import com.carrot.backend.productLike.domain.ProductLike;
import com.carrot.backend.user.dao.UserRepository;
import com.carrot.backend.user.domain.User;
import com.carrot.backend.util.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ProductLikeService {
    private final ProductLikeRepository productLikeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public boolean addProductLike(Integer productId, String userid){
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Product product = productRepository.findByProductId(productId).orElseThrow(()-> new DataNotFoundException("product not found"));
        if(isNotAlreadyLike(product,user)){
            productLikeRepository.save(new ProductLike(product, user));
            int prev = product.getProductLike();
            product.setProductLike(prev+1);
            return true;
        }else{
            ProductLike like = productLikeRepository.findByProductAndUser(product,user).orElseThrow(()-> new DataNotFoundException("like not found"));
            int prev = product.getProductLike();
            product.setProductLike(prev-1);
            productLikeRepository.delete(like);
            return false;
        }
    }
    private boolean isNotAlreadyLike(Product product, User user) {
        return productLikeRepository.findByProductAndUser(product, user).isEmpty();
    }

    public boolean checkLike(Integer productId, String userid) {
        User user = userRepository.findByUserid(userid).orElseThrow(()-> new DataNotFoundException("user not found"));
        Product product = productRepository.findByProductId(productId).orElseThrow(()-> new DataNotFoundException("product not found"));
        if(isNotAlreadyLike(product,user)){
            return false;
        }else{
            return true;
        }
    }
}
