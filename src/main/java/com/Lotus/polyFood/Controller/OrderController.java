package com.Lotus.polyFood.Controller;

import com.Lotus.polyFood.Config.VnpayConfig;
import com.Lotus.polyFood.Model.Order;
import com.Lotus.polyFood.Model.Product;
import com.Lotus.polyFood.Service.OrderService;
import com.Lotus.polyFood.payload.Request.OrderDetailRequest;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.DataAndList;
import com.Lotus.polyFood.payload.Response.MessageReponse;
import com.Lotus.polyFood.payload.Response.PaymentResponse;
import com.Lotus.polyFood.payload.Response.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("createOrder")
    public Response<Order> createOrder(@RequestBody DataAndList<Order, OrderDetailRequest> dataAndList){
        return orderService.createOrder(dataAndList);
    }

    @GetMapping("getOrderList")
    public Page<Order> orderList(Pageable pageable){
        return orderService.getOrderList(pageable);
    }
    @GetMapping("getOrder/{id}")
    public DataAndList<Order, OrderDetailRequest> getOrder(@PathVariable int id){
        return orderService.getOrder(id);
    }
    @PostMapping("payment/{id}")
    public Response<Order> updateOrderStatus(@PathVariable int id,@RequestParam(name = "statusId")int statusId){
        return orderService.updateOrderStatus(id,statusId);
    }
    @PostMapping("create_payment")
    public ResponseEntity<?> createPayment(@RequestBody Request<Order> orderRequest, HttpServletRequest request) throws UnsupportedEncodingException {
            return orderService.createpayment(orderRequest,request);
    }
    @GetMapping("payment_info")
    public ResponseEntity<?> transaction(
        @RequestParam(name = "vnp_Amount") String Amount,
        @RequestParam(name = "vnp_BankCode") String BankCode,
        @RequestParam(name = "vnp_TxnRef") String vnp_TxnRef,
        @RequestParam(name = "vnp_ResponseCode") String ResponseCode
    ){
        return orderService.transaction(Amount,BankCode,vnp_TxnRef,ResponseCode);
    }
 }
