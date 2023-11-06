package com.Lotus.polyFood.payload.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResponse<T> {
    private String error;
    private int status;
    private T product;
    private List<T> outStandings;
    private List<T> bestSellers;

    public ProductResponse(String error, int status, T product, List<T> outStandings, List<T> bestSellers) {
        this.error = error;
        this.status = status;
        this.product = product;
        this.outStandings = outStandings;
        this.bestSellers = bestSellers;
    }
}
