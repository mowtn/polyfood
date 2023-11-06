package com.Lotus.polyFood.Service.Impl;

import com.Lotus.polyFood.File.FileUpload;
import com.Lotus.polyFood.Model.CartItem;
import com.Lotus.polyFood.Model.OrderDetail;
import com.Lotus.polyFood.Model.Product;
import com.Lotus.polyFood.Model.ProductReview;
import com.Lotus.polyFood.Repository.DbContext.DbContext;
import com.Lotus.polyFood.Service.ProductService;
import com.Lotus.polyFood.payload.Request.ProductRequest;
import com.Lotus.polyFood.payload.Response.MessageReponse;
import com.Lotus.polyFood.payload.Response.ProductResponse;
import com.Lotus.polyFood.payload.Response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    DbContext dbContext;
    @Autowired
    FileUpload fileUpload;

    @Override
    public Page<Product> page(Pageable pageable) {
        return dbContext.productRepository.findAll(pageable);
    }
    @Override
    public <S extends Product> S save(S entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Product>> violations = validator.validate(entity);
        violations.forEach(x->{
            System.out.println("SaveError:"+x.getMessage());
        });
        if (violations.size()==0) return dbContext.productRepository.save(entity);
        else return null;
    }
    @Override
    public Response<Product> add(ProductRequest productRequest, MultipartFile avatarImageProduct) throws IOException {
        Product product = dbContext.productRepository.save(new Product(
                productRequest.getProductType(),
                productRequest.getNameProduct(),
                productRequest.getPrice(),
                fileUpload.uploadFile(avatarImageProduct),
                productRequest.getTitle(),
                productRequest.getDiscount(),
                productRequest.getStatus(),
                0,
                new Date()
        ));
        if (product==null) return new Response<>("add fail!",0,null);
        return new Response<>("Add done",1,product);
    }
    @Override
    public ResponseEntity<?> addImageAvatar(MultipartFile avatarImageProduct, int productId) throws IOException {
        Optional<Product> product = dbContext.productRepository.findById(productId);
        if (product.isEmpty()) return ResponseEntity.badRequest().body(new MessageReponse("Product not found"));
        String image = fileUpload.uploadFile(avatarImageProduct);
        product.get().setAvatarImageProduct(image);
        return ResponseEntity.ok(new MessageReponse("upload image successfully!"));
    }
    @Override
    public Response<Product> edit(int id, ProductRequest productRequest) {
        try{
            Optional<Product> product = dbContext.productRepository.findById(id);
            if (product.isEmpty()) return new Response<>("Product not found!",0,null);
            product.get().setProductType(productRequest.getProductType());
            product.get().setNameProduct(productRequest.getNameProduct());
            product.get().setPrice(productRequest.getPrice());
            product.get().setTitle(productRequest.getTitle());
            product.get().setDiscount(productRequest.getDiscount());
            product.get().setStatus(productRequest.getStatus());
            product.get().setUpdateAt(new Date());
            dbContext.productRepository.save(product.get());
            return new Response<>("Edited!",1,product.get());
        }catch (Exception ex){
            System.out.println("Error"+ex.getMessage());
            return new Response<>("Edit fail! please check your data request!",0,null);
        }
    }

    @Override
    public Response<Product> delete(int id) {
        try{
            Optional<Product> product = dbContext.productRepository.findById(id);
            if (product.isEmpty()) return new Response<>("Product not found!",0,null);

            for(OrderDetail orderDetail:dbContext.orderDetailRepository.findAll()){
                if (orderDetail.getProduct()==product.get())
                    dbContext.orderDetailRepository.delete(orderDetail);
            }
            for(ProductReview productReview:dbContext.productReviewRepository.findAll()){
                if (productReview.getProduct()==product.get())
                    dbContext.productReviewRepository.delete(productReview);
            }
            for(CartItem cartItem:dbContext.cartItemRepository.findAll()){
                if (cartItem.getProduct()==product.get())
                    dbContext.cartItemRepository.delete(cartItem);
            }
            dbContext.productRepository.deleteById(id);
            return new Response<>("deleted",1,null);
        }catch (Exception ex){
            System.out.println("Error"+ex.getMessage());
            return new Response<>("Edit fail! please check your action!",0,null);
        }
    }

    @Override
    public ProductResponse<Product> getProduct(int id) {
        Optional<Product> product = dbContext.productRepository.findById(id);
        if (product.isEmpty()) return new ProductResponse<>("Product not found!",0,null,null,null);
        int newNumber = product.get().getNumberOfViews()+1;
        product.get().setNumberOfViews(newNumber);
        dbContext.productRepository.save(product.get());

        List<Product> outStandings = new ArrayList<>();
        outStandings = dbContext.productRepository.getListOutStanding();
        List<Product> bestSeller = new ArrayList<>();
        List<Integer> listID= dbContext.orderDetailRepository.getBestSellers();
        for (Product pr :dbContext.productRepository.findAll()){
            if (listID.contains(pr.getProductId()))
                bestSeller.add(pr);
        }

        return new ProductResponse<>("Product",1,product.get(),outStandings,bestSeller);
    }

    @Override
    public Page<Product> searchByKeyword(Pageable pageable, String keyword) {
        return dbContext.productRepository.searchByKeyword(pageable,keyword);
    }


}
