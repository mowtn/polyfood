package com.Lotus.polyFood.payload.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageReponse {
    private String messeage;

    public MessageReponse(String messeage) {
        this.messeage = messeage;
    }
}
