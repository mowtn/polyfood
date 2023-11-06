package com.Lotus.polyFood.Service.Impl;

import com.Lotus.polyFood.Config.VnpayConfig;
import com.Lotus.polyFood.Email.EmailService;
import com.Lotus.polyFood.Model.Order;
import com.Lotus.polyFood.Model.OrderDetail;
import com.Lotus.polyFood.Model.OrderStatus;
import com.Lotus.polyFood.Repository.DbContext.DbContext;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    DbContext dbContext;
    VnpayConfig Config;
    @Autowired
    EmailService emailService;
    @Override
    public Page<Order> getOrderList(Pageable pageable) {
        return dbContext.orderRepository.findAll(pageable);
    }

    @Override
    public DataAndList<Order, OrderDetailRequest> getOrder(int id) {
        try {
            Optional<Order> order = dbContext.orderRepository.findById(id);
            if (order.isEmpty()) return new DataAndList<>("Order not found!",0,null,null);

            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
            if (!authority.contains("ROLE_ADMIN")&&
                    !userDetails.getUsername().equals(order.get().getUser().getUsername()))
                return new DataAndList<>("not allow",0,null,null);


            List<OrderDetailRequest> orderDetailRequests = new ArrayList<>();
            for (OrderDetail orderDetail:dbContext.orderDetailRepository.findAll()){
                if (orderDetail.getOrder()==order.get()){
                    orderDetailRequests.add(new OrderDetailRequest(orderDetail.getProduct(),orderDetail.getQuantity()));
                }
            }
            return new DataAndList<>("found",1,order.get(),orderDetailRequests);
        }catch (Exception ex){
            System.out.println("error: "+ex.getMessage());
            return new DataAndList<>(ex.getMessage(),0,null,null);
        }
    }

    @Override
    public Response<Order> createOrder(DataAndList<Order, OrderDetailRequest> dataAndList) {
        try {
            if (!dbContext.userRepository.existsByUsername(
                    dataAndList.getData().getUser().getUsername()))
                return new Response<>("User not found!",0,null);
            Order order = dbContext.orderRepository.save(new Order(
                    dataAndList.getData().getPayment(),
                    dataAndList.getData().getUser(),
                    dataAndList.getData().getOriginalPrice(),
                    dataAndList.getData().getActualPrice(),
                    dataAndList.getData().getFullname(),
                    dataAndList.getData().getPhone(),
                    dataAndList.getData().getEmail(),
                    dataAndList.getData().getAddress(),
                    dataAndList.getData().getOrderStatus(),
                    new Date()
            ));

            if (dataAndList.getList().isEmpty()) return new Response<>("List product is null",0,null);
            for (OrderDetailRequest orderDetailRequest: dataAndList.getList()){
                double priceTotal = orderDetailRequest.getProduct().getPrice() * orderDetailRequest.getQuantity();
                dbContext.orderDetailRepository.save(new OrderDetail(
                        order,
                        orderDetailRequest.getProduct(),
                        priceTotal,
                        orderDetailRequest.getQuantity(),
                        new Date()
                ));
            }
            return new Response<>("Created Order",1,order);
        }catch (Exception ex){
            System.out.println("error: "+ex.getMessage());
            return new Response<>("Error:"+ex.getMessage(),0,null);
        }
    }

    @Override
    public Response<Order> updateOrderStatus(int id, int statusId) {
        Optional<Order> order = dbContext.orderRepository.findById(id);
        if (order.isEmpty()) return new Response<>("Order not found!",1,null);
        Optional<OrderStatus> orderStatus = dbContext.orderStatusRepository.findById(statusId);
        if (orderStatus.isEmpty()) return new Response<>("Order not found!",1,null);
        order.get().setOrderStatus(orderStatus.get());
        dbContext.orderRepository.save(order.get());
        String mail = emailService.BuildEmailChangeStatusOrder(orderStatus.get().getStatusName(),String.valueOf(id));

        String email;
        if (order.get().getUser()==null) email=order.get().getEmail();
        else email = order.get().getUser().getEmail();

        emailService.send(email,mail, "Cập Nhật Trạng thái đơn hàng");
        return new Response<>("Updated",1,order.get());
    }

    @Override
    public ResponseEntity<?> createpayment(Request<Order> orderRequest, HttpServletRequest request) throws UnsupportedEncodingException {
        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");
        if (orderRequest.getData() == null)
            return ResponseEntity.badRequest().body(new MessageReponse("order not found"));
        long amount = orderRequest.getData().getActualPrice()*100;
        String vnp_TxnRef = Config.getRandomNumber(8)+orderRequest.getData().getOrderId();
        String vnp_IpAddr = Config.getIpAddress(request);

        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnp_Params.put("vnp_BankCode", bankCode);
//        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: #" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_OrderType", orderType);

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setMessage("successfully");
        paymentResponse.setStatus("ok");
        paymentResponse.setURL(paymentUrl);
        return ResponseEntity.ok(paymentResponse);
    }

    @Override
    public ResponseEntity<?> transaction(String amount, String bankCode, String vnp_TxnRef, String responseCode) {
        if (!responseCode.equals("00")){
            return ResponseEntity.badRequest().body(new MessageReponse("fail to payment"));
        }else {
            int orderid = Integer.parseInt(vnp_TxnRef.substring(8));
            //5: đã thanh toán
            updateOrderStatus(orderid,5);
            return ResponseEntity.ok(new MessageReponse("pay successfully"));
        }
    }
}
