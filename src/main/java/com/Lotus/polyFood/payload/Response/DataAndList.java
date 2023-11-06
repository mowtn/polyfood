package com.Lotus.polyFood.payload.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataAndList<O,P> {
    private String error;
    private int status;
    private O data;
    private List<P> list;

    public DataAndList(String error, int status, O data, List<P> list) {
        this.error = error;
        this.status = status;
        this.data = data;
        this.list = list;
    }
}
