package com.Lotus.polyFood.Controller;

import com.Lotus.polyFood.Model.ProductType;
import com.Lotus.polyFood.Service.ProductTypeService;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("productType")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProductTypeController {
    @Autowired
    ProductTypeService productTypeService;
    @GetMapping("page")
    public Page<ProductType> page(Pageable pageable){
        return productTypeService.page(pageable);
    }
    @PostMapping("add")
    public Response<ProductType> add(@RequestBody Request<ProductType> productTypeRequest){
        return productTypeService.add(productTypeRequest);
    }
    @PutMapping("edit/{id}")
    public Response<ProductType> edit(@PathVariable int id,@RequestBody Request<ProductType> productTypeRequest){
        return productTypeService.edit(id,productTypeRequest);
    }
    @DeleteMapping("delete/{id}")
    public Response<ProductType> delete(@PathVariable int id){
        return productTypeService.delete(id);
    }
}
