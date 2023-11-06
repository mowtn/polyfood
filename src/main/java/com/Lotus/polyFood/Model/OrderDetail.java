package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "tb_orderDetail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    @JsonIgnoreProperties(value = "orderDetails")
    Order order;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties(value = "orderDetails")
    Product product;
    private double priceTotal;
    private int quantity;
    private Date createAt;
    private Date updateAt;

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product, double priceTotal, int quantity, Date createAt) {
        this.order = order;
        this.product = product;
        this.priceTotal = priceTotal;
        this.quantity = quantity;
        this.createAt = createAt;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(double priceTotal) {
        this.priceTotal = priceTotal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}