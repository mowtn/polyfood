package com.Lotus.polyFood.Service.Impl;

import com.Lotus.polyFood.File.FileUpload;
import com.Lotus.polyFood.Model.Order;
import com.Lotus.polyFood.Model.OrderDetail;
import com.Lotus.polyFood.Model.ProductReview;
import com.Lotus.polyFood.Repository.DbContext.DbContext;
import com.Lotus.polyFood.Service.ProductReviewService;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {
    @Autowired
    DbContext dbContext;


    @Override
    public <S extends ProductReview> S save(S entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ProductReview>> violations = validator.validate(entity);
        violations.forEach(x->{
            System.out.println("SaveError:"+x.getMessage());
        });
        if (violations.size()==0) return dbContext.productReviewRepository.save(entity);
        else return null;
    }

    @Override
    public Response<ProductReview> addReview(Request<ProductReview> productReviewRequest) {
        try {
            if (!dbContext.userRepository.existsByUsername(
                    productReviewRequest.getData().getUser().getUsername()))
                return new Response<>("User not found!",0,null);
            if (!dbContext.productRepository.existsByNameProduct(
                    productReviewRequest.getData().getProduct().getNameProduct()))
                return new Response<>("Product not found",0,null);

            boolean isTrue = false;
            for(Order order:dbContext.orderRepository.findAll()){
                if (order.getUser().getUsername().equals(productReviewRequest.getData().getUser().getUsername())){
                    for (OrderDetail orderDetail:dbContext.orderDetailRepository.findAll()){
                        if (orderDetail.getOrder()==order&&orderDetail.getProduct().getProductId()==productReviewRequest.getData().getProduct().getProductId()){
                            isTrue = true;
                        }
                    }
                }
            }
            if (!isTrue) return new Response<>("Not allow to review ",0,null);

            ProductReview productReview = save(new ProductReview(
                    productReviewRequest.getData().getProduct(),
                    productReviewRequest.getData().getUser(),
                    productReviewRequest.getData().getContentRate(),
                    productReviewRequest.getData().getPointEvaluation(),
                    productReviewRequest.getData().getContentSeen(),
                    1,
                    new Date()
            ));
            return new Response<>("Added review",1,productReview);
        }catch (Exception ex){
            System.out.println("error: "+ex.getMessage());
            return new Response<>("Error:"+ex.getMessage(),0,null);
        }
    }

    @Override
    public Response<ProductReview> editReview(int id, Request<ProductReview> productReviewRequest) {
        try{
            Optional<ProductReview> productReview = dbContext.productReviewRepository.findById(id);
            if (productReview.isEmpty()) return new Response<>("Review not found!",0,null);

            boolean isTrue = checkUser(productReview.get().getUser().getUsername());
            if (!isTrue) return new Response<>("Not allow!",0,null);

            if (!dbContext.userRepository.existsByUsername(
                    productReviewRequest.getData().getUser().getUsername()))
                return new Response<>("User not found!",0,null);
            if (!dbContext.productRepository.existsByNameProduct(
                    productReviewRequest.getData().getProduct().getNameProduct()))
                return new Response<>("Product not found",0,null);
            productReview.get().setProduct(productReviewRequest.getData().getProduct());
            productReview.get().setUser(productReviewRequest.getData().getUser());
            productReview.get().setContentRate(productReviewRequest.getData().getContentRate());
            productReview.get().setPointEvaluation(productReviewRequest.getData().getPointEvaluation());
            productReview.get().setContentSeen(productReviewRequest.getData().getContentSeen());
            productReview.get().setStatus(productReviewRequest.getStatus());
            productReview.get().setUpdateAt(new Date());
            save(productReview.get());
            return new Response<>("edited",1,productReview.get());
        }catch (Exception ex){
            System.out.println("error: "+ex.getMessage());
            return new Response<>("Error:"+ex.getMessage(),0,null);
        }
    }

    @Override
    public Response<ProductReview> delete(int id) {
        try {
            Optional<ProductReview> productReview = dbContext.productReviewRepository.findById(id);
            if (productReview.isEmpty()) return new Response<>("Review not found!",0,null);

            boolean isTrue = checkUser(productReview.get().getUser().getUsername());
            if (!isTrue) return new Response<>("Not allow!",0,null);

            dbContext.productReviewRepository.delete(productReview.get());
            return new Response<>("deleted",1,null);
        }catch (Exception ex){
            System.out.println("error: "+ex.getMessage());
            return new Response<>("Error:"+ex.getMessage(),0,null);
        }
    }

    @Override
    public Response<ProductReview> getReview(int id) {
        Optional<ProductReview> productReview = dbContext.productReviewRepository.findById(id);
        if (productReview.isEmpty()) return new Response<>("Review not found!",0,null);

        boolean isTrue = checkUser(productReview.get().getUser().getUsername());
        return (isTrue) ?  new Response<>("found",1,productReview.get()) : new Response<>("Not allow!",0,null);
    }

    @Override
    public Page<ProductReview> page(Pageable pageable) {
        return dbContext.productReviewRepository.findAll(pageable);
    }
    private boolean checkUser(String username){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (authority.contains("ROLE_ADMIN")) return true;
        return (userDetails.getUsername().equals(username))? true:false;
    }
}
