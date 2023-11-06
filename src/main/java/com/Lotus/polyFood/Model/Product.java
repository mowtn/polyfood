package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productTypeId")
    @JsonIgnoreProperties(value = "products")
    ProductType productType;
    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = "product")
    Set<ProductReview> productReviews;
    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = "product")
    Set<CartItem> cartItems;
    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties(value = "product")
    Set<OrderDetail> orderDetails;
    @NotBlank
    private String nameProduct;
    @NotNull
    private double price;
    @NotBlank
    private String avatarImageProduct;
    @NotBlank
    private String title;
    private int discount;
    private int status;
    private int numberOfViews;
    private Date createAt;
    private Date updateAt;


    public Product() {
    }

    public Product(ProductType productType, String nameProduct, double price, String avatarImageProduct, String title, int discount, int status, int numberOfViews, Date createAt) {
        this.productType = productType;
        this.nameProduct = nameProduct;
        this.avatarImageProduct = avatarImageProduct;
        this.price = price;
        this.title = title;
        this.discount=discount;
        this.status = status;
        this.numberOfViews = numberOfViews;
        this.createAt = createAt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getAvatarImageProduct() {
        return avatarImageProduct;
    }

    public void setAvatarImageProduct(String avatarImageProduct) {
        this.avatarImageProduct = avatarImageProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumberOfViews() {
        return numberOfViews;
    }

    public void setNumberOfViews(int numberOfViews) {
        this.numberOfViews = numberOfViews;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
