package com.Lotus.polyFood.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tb_producttype")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productTypeId;

    @NotBlank
    private String nameProductType;
    private String imageTypeProduct;
    private Date createAt;
    private Date updateAt;
    @OneToMany(mappedBy = "productType")
    @JsonIgnoreProperties(value = "productType")
    Set<Product> products;

    public ProductType() {
    }

    public ProductType(String nameProductType, String imageTypeProduct, Date createAt) {
        this.nameProductType = nameProductType;
        this.imageTypeProduct = imageTypeProduct;
        this.createAt = createAt;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getNameProductType() {
        return nameProductType;
    }

    public void setNameProductType(String nameProductType) {
        this.nameProductType = nameProductType;
    }

    public String getImageTypeProduct() {
        return imageTypeProduct;
    }

    public void setImageTypeProduct(String imageTypeProduct) {
        this.imageTypeProduct = imageTypeProduct;
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
