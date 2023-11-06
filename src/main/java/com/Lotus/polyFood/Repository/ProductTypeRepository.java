package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType,Integer> {
}
