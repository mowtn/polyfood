package com.Lotus.polyFood.payload.Request;

import com.Lotus.polyFood.Model.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProductRequest {
    private ProductType productType;
    private String nameProduct;
    private double price;
    private MultipartFile avatarImageProduct;
    private String title;
    private int discount;
    private int status;

}
