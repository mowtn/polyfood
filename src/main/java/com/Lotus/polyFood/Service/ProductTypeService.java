package com.Lotus.polyFood.Service;

import com.Lotus.polyFood.Model.ProductType;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductTypeService {
    Page<ProductType> page(Pageable pageable);

    <S extends ProductType> S save(S entity);

    Response<ProductType> add(Request<ProductType> productTypeRequest);

    Response<ProductType> edit(int id, Request<ProductType> productTypeRequest);

    Response<ProductType> delete(int id);
}
