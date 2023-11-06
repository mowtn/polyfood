package com.Lotus.polyFood.Controller;

import com.Lotus.polyFood.Model.ProductReview;
import com.Lotus.polyFood.Service.ProductReviewService;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productreview")
public class ProductReviewController {
    @Autowired
    ProductReviewService productReviewService;
    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response<ProductReview> addReview(@RequestBody Request<ProductReview> productReviewRequest){
        return productReviewService.addReview(productReviewRequest);
    }
    @PutMapping("edit/{id}")
    public Response<ProductReview> editReview(@PathVariable int id,@RequestBody Request<ProductReview> productReviewRequest){
        return productReviewService.editReview(id,productReviewRequest);
    }
    @DeleteMapping("delete/{id}")
    public Response<ProductReview> deleteReview(@PathVariable int id){
        return productReviewService.delete(id);
    }
    @GetMapping("review/{id}")
    public Response<ProductReview> getReview(@PathVariable int id){
        return productReviewService.getReview(id);
    }
    @GetMapping("page")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<ProductReview> page(Pageable pageable){
        return productReviewService.page(pageable);
    }
}
