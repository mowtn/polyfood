package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "SELECT * FROM tb_product where name_product like %:keyword%",nativeQuery = true)
    Page<Product> searchByKeyword(Pageable pageable,@Param("keyword") String keyword);
    Boolean existsByNameProduct(String nameProduct);
    @Query(value = "SELECT * FROM tb_product order by number_of_views desc limit 10",nativeQuery = true)
    List<Product> getListOutStanding();
}
