package com.talent.authapi.repositories;

import com.talent.authapi.entities.LikeList;
import com.talent.authapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList, Long> {

	Optional<LikeList> findByUserEmail(String userEmail);

    boolean existsByProductsIn(List<Product> products);

    void deleteByProductsIn(List<Product> products);

	Optional<LikeList> findByUserId(Long userId);

}
