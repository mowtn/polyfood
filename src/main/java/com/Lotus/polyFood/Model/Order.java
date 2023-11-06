package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paymentId")
    @JsonIgnoreProperties(value = "orders")
    Payment payment;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "orders")
    User user;
    @NotNull
    private int originalPrice;
    private int actualPrice;
    private String fullname;
    @Size(max = 11)
    private String phone;
    @Email
    private String email;
    private String address;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderStatusId")
    @JsonIgnoreProperties(value = "orders")
    OrderStatus orderStatus;
    private Date createAt;
    private Date updateAt;
    @OneToMany(mappedBy = "order")
    @JsonIgnoreProperties(value = "order")
    Set<OrderDetail> orderDetails;

    public Order() {
    }

    public Order(Payment payment, User user, int originalPrice, int actualPrice, String fullname, String phone, String email, String address, OrderStatus orderStatus, Date createAt) {
        this.payment = payment;
        this.user = user;
        this.originalPrice = originalPrice;
        this.actualPrice = actualPrice;
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.orderStatus = orderStatus;
        this.createAt = createAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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
