package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_cart")
public class Carts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "carts")
    User user;
    private Date createAt;
    private Date updateAt;
    @OneToMany(mappedBy = "carts")
    @JsonIgnoreProperties(value = "carts")
    Set<CartItem> cartItems;

    public Carts() {
    }

    public Carts(User user, Date createAt) {
        this.user = user;
        this.createAt = createAt;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
