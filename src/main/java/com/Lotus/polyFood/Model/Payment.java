package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    @NotBlank
    @Size(max = 50)
    private String paymentMethod;
    @NotBlank
    private int status;
    private Date createAt;
    private Date updateAt;

    @OneToMany(mappedBy = "payment")
    @JsonIgnoreProperties(value = "payment")
    Set<Order> orders;

    public Payment() {
    }

    public Payment(String paymentMethod, int status, Date createAt) {
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createAt = createAt;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
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
