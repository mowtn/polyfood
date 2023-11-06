package com.Lotus.polyFood.Repository.DbContext;

import com.Lotus.polyFood.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbContext {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public DecentralizationRepository decentralizationRepository;
    @Autowired
    public CodeRegistrationRepository codeRegistrationRepository;
    @Autowired
    public ProductRepository productRepository;
    @Autowired
    public ProductTypeRepository productTypeRepository;
    @Autowired
    public CartItemRepository cartItemRepository;
    @Autowired
    public OrderRepository orderRepository;
    @Autowired
    public OrderStatusRepository orderStatusRepository;
    @Autowired
    public OrderDetailRepository orderDetailRepository;
    @Autowired
    public ProductReviewRepository productReviewRepository;
    @Autowired
    public PaymentRepository paymentRepository;
}
