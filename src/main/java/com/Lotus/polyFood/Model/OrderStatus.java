package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "tb_order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderStatusId;
    @NotBlank
    private String statusName;
    @OneToMany(mappedBy = "orderStatus")
    @JsonIgnoreProperties(value = "orderStatus")
    Set<Order> orders;

    public OrderStatus() {
    }

    public OrderStatus(String statusName) {
        this.statusName = statusName;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
