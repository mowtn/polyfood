package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Entity
@Table(name = "tb_product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productReviewId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties(value = "productReviews")
    Product product;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties(value = "productReviews")
    User user;
    @Size(min = 10)
    private String contentRate;
    @Max(value = 5)
    private int pointEvaluation;
    private String contentSeen;
    private int status;
    private Date createAt;
    private Date updateAt;

    public ProductReview() {
    }

    public ProductReview(Product product, User user, String contentRate, int pointEvaluation, String contentSeen, int status, Date createAt) {
        this.product = product;
        this.user = user;
        this.contentRate = contentRate;
        this.pointEvaluation = pointEvaluation;
        this.contentSeen = contentSeen;
        this.status = status;
        this.createAt = createAt;
    }

    public int getProductReviewId() {
        return productReviewId;
    }

    public void setProductReviewId(int productReviewId) {
        this.productReviewId = productReviewId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContentRate() {
        return contentRate;
    }

    public void setContentRate(String contentRate) {
        this.contentRate = contentRate;
    }

    public int getPointEvaluation() {
        return pointEvaluation;
    }

    public void setPointEvaluation(int pointEvaluation) {
        this.pointEvaluation = pointEvaluation;
    }

    public String getContentSeen() {
        return contentSeen;
    }

    public void setContentSeen(String contentSeen) {
        this.contentSeen = contentSeen;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
