package com.Lotus.polyFood.Service;

import com.Lotus.polyFood.Model.Product;
import com.Lotus.polyFood.payload.Request.ProductRequest;
import com.Lotus.polyFood.payload.Response.ProductResponse;
import com.Lotus.polyFood.payload.Response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    Page<Product> page(Pageable pageable);

    <S extends Product> S save(S entity);

    Response<Product> add(ProductRequest productRequest, MultipartFile avatarImageProduct) throws IOException;

    Response<Product> edit(int id, ProductRequest productRequest);

    Response<Product> delete(int id);

    ProductResponse<Product> getProduct(int id);

    Page<Product> searchByKeyword(Pageable pageable, String keyword);

    ResponseEntity<?> addImageAvatar(MultipartFile avatarImageProduct, int productId) throws IOException;
}
