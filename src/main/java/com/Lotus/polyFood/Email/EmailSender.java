package com.Lotus.polyFood.Email;

public interface EmailSender {
    void send(String emailReceive, String mail,String title);

    String buildEmail(String name, String code,String link);

    String BuildEmailChangeStatusOrder(String status, String orderId);
}
