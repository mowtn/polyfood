package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.OrderDetail;
import com.Lotus.polyFood.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    @Query(value = "SELECT product_id FROM tb_order_detail\n" +
            "group by product_id\n" +
            "order by sum(quantity) desc\n" +
            "limit 10",nativeQuery = true)
    List<Integer> getBestSellers();
}
