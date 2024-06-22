package com.talent.authapi.controllers;

import com.talent.authapi.dtos.LikeListRequestDTO;
import com.talent.authapi.entities.LikeList;
import com.talent.authapi.entities.Product;
import com.talent.authapi.entities.User;
import com.talent.authapi.services.LikeListService;
import com.talent.authapi.services.ProductService;
import com.talent.authapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/like-list")
public class LikeListController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeListService likeListService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<String> createLikeList(@RequestBody LikeListRequestDTO likeListRequestDTO) {
        User user = userService.findById(likeListRequestDTO.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().body("找不到指定的使用者: " + likeListRequestDTO.getUserId());
        }

        if (!likeListRequestDTO.getAccount().equals(user.getAccount())) {
            user.setAccount(likeListRequestDTO.getAccount());
            userService.saveUser(user);
        }

        List<Long> productIds = likeListRequestDTO.getProductIds();
        List<Product> productsToUpdate = productIds.stream()
                .map(productId -> productService.findById(productId))
                .filter(product -> product != null)
                .collect(Collectors.toList());

        if (productsToUpdate.size() != productIds.size()) {
            return ResponseEntity.badRequest().body("找不到指定的產品");
        }

        double totalFee = calculateTotalFee(productsToUpdate);
        double totalAmount = calculateTotalAmount(productsToUpdate);
        

        LikeList likeList = new LikeList(user, productsToUpdate, productsToUpdate.size(), user.getAccount(), totalFee, totalAmount);

        likeListService.addLikeList(likeList);

        return ResponseEntity.ok("成功創建喜好清單");
    }

    @PostMapping("/add-product/{likeListId}/{productId}")
    public ResponseEntity<String> addProductToLikeList(@PathVariable Long likeListId, @PathVariable Long productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            return ResponseEntity.badRequest().body("找不到指定的產品: " + productId);
        }

        likeListService.addProductsToLikeList(likeListId, List.of(product));

        return ResponseEntity.ok("成功添加產品到喜好清單");
    }

    @DeleteMapping("/remove-product/{likeListId}/{productId}")
    public ResponseEntity<String> removeProductFromLikeList(@PathVariable Long likeListId, @PathVariable Long productId) {
        Product product = productService.findById(productId);
        if (product == null) {
            return ResponseEntity.badRequest().body("找不到指定的產品: " + productId);
        }

        likeListService.removeProductFromLikeList(likeListId, product);

        return ResponseEntity.ok("成功從喜好清單中刪除產品");
    }

    @PutMapping("/update-products/{likeListId}")
    public ResponseEntity<String> updateProductsInLikeList(@PathVariable Long likeListId, @RequestBody LikeListRequestDTO likeListRequestDTO) {
        User user = userService.findById(likeListRequestDTO.getUserId());
        if (user == null) {
            return ResponseEntity.badRequest().body("找不到指定的使用者: " + likeListRequestDTO.getUserId());
        }

        if (!likeListRequestDTO.getAccount().equals(user.getAccount())) {
            user.setAccount(likeListRequestDTO.getAccount());
            userService.saveUser(user);
        }

        List<Long> productIds = likeListRequestDTO.getProductIds();
        List<Product> productsToUpdate = productIds.stream()
                .map(productId -> productService.findById(productId))
                .filter(product -> product != null)
                .collect(Collectors.toList());

        if (productsToUpdate.size() != productIds.size()) {
            return ResponseEntity.badRequest().body("找不到指定的產品");
        }

        likeListService.updateProductsInLikeList(likeListId, productIds);

        return ResponseEntity.ok("成功更新喜好清單的產品列表");
    }

    @GetMapping("/{likeListId}")
    public ResponseEntity<LikeList> getLikeListById(@PathVariable Long likeListId) {
        Optional<LikeList> optionalLikeList = likeListService.getLikeListById(likeListId);
        return optionalLikeList.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLikeListsByUserId(@PathVariable Long userId) {
        User user = userService.findById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<LikeList> likeLists = likeListService.getLikeListsByUserId(userId);
        return ResponseEntity.ok(likeLists);
    }
    
    // Helper method to calculate total fee
    private double calculateTotalFee(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }

    // Helper method to calculate total amount
    private double calculateTotalAmount(List<Product> products) {
        return products.stream().mapToDouble(p -> p.getFeeRate() * p.getPrice()).sum();
    }
}
