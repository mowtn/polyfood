package com.Lotus.polyFood.Service;

import com.Lotus.polyFood.Model.ProductReview;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductReviewService {
    <S extends ProductReview> S save(S entity);

    Response<ProductReview> addReview(Request<ProductReview> productReviewRequest);

    Response<ProductReview> editReview(int id, Request<ProductReview> productReviewRequest);

    Response<ProductReview> delete(int id);

    Response<ProductReview> getReview(int id);

    Page<ProductReview> page(Pageable pageable);


}
