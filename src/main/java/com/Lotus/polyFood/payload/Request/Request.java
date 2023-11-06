package com.Lotus.polyFood.payload.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request<T> {
    private String error;
    private int status;
    private T data;

    public Request(String error, int status, T data) {
        this.error = error;
        this.status = status;
        this.data = data;
    }
}
