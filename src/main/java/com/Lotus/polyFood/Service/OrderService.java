package com.Lotus.polyFood.Service;

import com.Lotus.polyFood.Model.Order;
import com.Lotus.polyFood.payload.Request.OrderDetailRequest;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.DataAndList;
import com.Lotus.polyFood.payload.Response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface OrderService {
    Page<Order> getOrderList(Pageable pageable);

    DataAndList<Order, OrderDetailRequest> getOrder(int id);

    Response<Order> createOrder(DataAndList<Order, OrderDetailRequest> dataAndList);

    Response<Order> updateOrderStatus(int id, int statusId);

//    ResponseEntity<?> createpayment(Request<Order> orderRequest) throws UnsupportedEncodingException;

    ResponseEntity<?> createpayment(Request<Order> orderRequest, HttpServletRequest request) throws UnsupportedEncodingException;

    ResponseEntity<?> transaction(String amount, String bankCode, String vnp_TxnRef, String responseCode);
}
