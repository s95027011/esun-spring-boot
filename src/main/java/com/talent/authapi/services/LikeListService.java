package com.talent.authapi.services;

import com.talent.authapi.entities.LikeList;
import com.talent.authapi.entities.Product;
import com.talent.authapi.repositories.LikeListRepository;
import com.talent.authapi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LikeListService {

    @Autowired
    private LikeListRepository likeListRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Optional<LikeList> getLikeListByUserId(Long userId) {
        return likeListRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<LikeList> getLikeListById(Long id) {
        return likeListRepository.findById(id);
    }

    @Transactional
    public LikeList addLikeList(LikeList likeList) {
        return likeListRepository.save(likeList);
    }

    @Transactional
    public LikeList updateLikeList(Long id, LikeList likeListDetails) {
        return likeListRepository.findById(id)
                .map(likeList -> {
                    likeList.setUser(likeListDetails.getUser());
                    likeList.setProducts(likeListDetails.getProducts());
                    likeList.setNumberOfProducts(likeListDetails.getNumberOfProducts());
                    likeList.setAccount(likeListDetails.getAccount());
                    likeList.setTotalFee(likeListDetails.getTotalFee());
                    likeList.setTotalAmount(likeListDetails.getTotalAmount());
                    return likeListRepository.save(likeList);
                })
                .orElseThrow(() -> new RuntimeException("找不到指定的喜好清單：" + id));
    }

    @Transactional
    public void deleteLikeList(Long id) {
        likeListRepository.deleteById(id);
    }

    @Transactional
    public void addProductsToLikeList(Long likeListId, List<Product> products) {
        Optional<LikeList> optionalLikeList = likeListRepository.findById(likeListId);
        optionalLikeList.ifPresent(likeList -> {
            likeList.getProducts().addAll(products);
            updateLikeList(likeListId, likeList); // Assuming updateLikeList method handles saving changes
        });
    }

    @Transactional
    public void removeProductFromLikeList(Long likeListId, Product product) {
        Optional<LikeList> optionalLikeList = likeListRepository.findById(likeListId);
        optionalLikeList.ifPresent(likeList -> {
            likeList.getProducts().remove(product);
            likeList.setNumberOfProducts(likeList.getProducts().size());
            updateProductsInLikeList(likeList);
        });
    }
    
    @Transactional
    private void updateProductsInLikeList(LikeList likeList) {
        likeList.setNumberOfProducts(likeList.getProducts().size());
        likeList.setTotalFee(calculateTotalFee(likeList.getProducts()));
        likeList.setTotalAmount(calculateTotalAmount(likeList.getProducts()));
        likeListRepository.save(likeList);
    }
//    public void removeProductFromLikeList(Long likeListId, Product product) {
//        Optional<LikeList> optionalLikeList = likeListRepository.findById(likeListId);
//        optionalLikeList.ifPresent(likeList -> {
//            likeList.getProducts().remove(product);
//            if (likeList.getProducts().isEmpty()) {
//                deleteLikeList(likeListId); // Assuming deleteLikeList method handles deletion
//            } else {
//                updateLikeList(likeListId, likeList); // Assuming updateLikeList method handles saving changes
//            }
//        });
//    }

    @Transactional
    public void updateProductsInLikeList(Long likeListId, List<Long> productIds) {
        Optional<LikeList> optionalLikeList = likeListRepository.findById(likeListId);
        optionalLikeList.ifPresent(likeList -> {
            List<Product> productsToUpdate = new ArrayList<>();
            for (Long productId : productIds) {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("找不到指定的產品：" + productId));
                productsToUpdate.add(product);
            }

            likeList.setProducts(productsToUpdate);
            likeList.setNumberOfProducts(productsToUpdate.size());
            likeList.setTotalFee(calculateTotalFee(productsToUpdate));
            likeList.setTotalAmount(calculateTotalAmount(productsToUpdate));
            
            updateLikeList(likeListId, likeList); // Assuming updateLikeList method handles saving changes
        });
    }
    
    @Transactional
    public Optional<LikeList> getLikeListsByUserId(Long userId) {
        return likeListRepository.findByUserId(userId);
    }

    private double calculateTotalFee(List<Product> products) {
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    private double calculateTotalAmount(List<Product> products) {
        return products.stream()
                .mapToDouble(product -> product.getFeeRate() * product.getPrice())
                .sum();
    }
}
