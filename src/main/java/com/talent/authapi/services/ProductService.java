package com.talent.authapi.services;

import com.talent.authapi.entities.Product;
import com.talent.authapi.entities.User;
import com.talent.authapi.repositories.LikeListRepository;
import com.talent.authapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LikeListRepository likeListRepository;
    
    public Product findById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setProductName(productDetails.getProductName());
                    product.setPrice(productDetails.getPrice());
                    product.setFeeRate(productDetails.getFeeRate());
                    product.setVisible(productDetails.isVisible());
                    return productRepository.save(product);
                }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

//    @Transactional
//    public void deleteProduct(Long id) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        
//        if (likeListRepository.existsByProduct(product)) {
//            throw new RuntimeException("Product is in a LikeList, cannot delete");
//        }
//
//        productRepository.delete(product);
//    }
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        if (likeListRepository.existsByProductsIn(Collections.singletonList(product))) {
            throw new RuntimeException("Product is in a LikeList, cannot delete");
        }

        productRepository.delete(product);
    }


    public Product updateVisibility(Long id, boolean visibility) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setVisible(visibility);
                    return productRepository.save(product);
                }).orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
