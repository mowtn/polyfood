package com.Lotus.polyFood.payload.Request;

import com.Lotus.polyFood.Model.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailRequest {
    private Product product;
    private int quantity;

    public OrderDetailRequest(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
