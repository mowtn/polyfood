package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview,Integer> {
}
