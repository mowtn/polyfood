package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
@Table(name = "tb_cartItem")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties(value = "cartItems")
    Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cartId")
    @JsonIgnoreProperties(value = "cartItems")
    Carts carts;
    @NotNull
    private int quantity;
    private Date createAt;
    private Date updateAt;

    public CartItem() {
    }

    public CartItem(Product product, Carts carts, int quantity, Date createAt) {
        this.product = product;
        this.carts = carts;
        this.quantity = quantity;
        this.createAt = createAt;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Carts getCarts() {
        return carts;
    }

    public void setCarts(Carts carts) {
        this.carts = carts;
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
