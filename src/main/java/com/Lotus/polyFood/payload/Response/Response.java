package com.Lotus.polyFood.payload.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<T> {
    private String error;
    private int status;
    private T data;

    public Response(String error, int status, T data) {
        this.error = error;
        this.status = status;
        this.data = data;
    }
}
