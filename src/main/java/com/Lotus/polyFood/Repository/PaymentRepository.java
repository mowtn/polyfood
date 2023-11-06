package com.Lotus.polyFood.Repository;

import com.Lotus.polyFood.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Integer> {
}
