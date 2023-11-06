package com.Lotus.polyFood.Service.Impl;

import com.Lotus.polyFood.Model.Product;
import com.Lotus.polyFood.Model.ProductType;
import com.Lotus.polyFood.Repository.DbContext.DbContext;
import com.Lotus.polyFood.Service.ProductTypeService;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.Response;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    DbContext dbContext;

    @Override
    public Page<ProductType> page(Pageable pageable) {
        return dbContext.productTypeRepository.findAll(pageable);
    }

    @Override
    public <S extends ProductType> S save(S entity) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ProductType>> violations = validator.validate(entity);
        violations.forEach(x->{
            System.out.println("SaveError:"+x.getMessage());
        });
        if (violations.size()==0) return dbContext.productTypeRepository.save(entity);
        else return null;
    }
    @Override
    public Response<ProductType> add(Request<ProductType> productTypeRequest) {
        for (ProductType productType:dbContext.productTypeRepository.findAll()){
            if (productTypeRequest.getData().getNameProductType().equals(productType.getNameProductType()))
                return new Response<>("Name type product exists!",0,null);
        }
        ProductType productType = save(new ProductType(
                productTypeRequest.getData().getNameProductType(),
                ".image/productType/"+productTypeRequest.getData().getImageTypeProduct(),
                new Date()
        ));
        return new Response<>("added type product",1,productType);
    }

    @Override
    public Response<ProductType> edit(int id, Request<ProductType> productTypeRequest) {
        try{
            Optional<ProductType> productType = dbContext.productTypeRepository.findById(id);
            if (productType.isEmpty()) return new Response<>("Type Product not found!",0,null);

            for (ProductType productType1:dbContext.productTypeRepository.findAll()){
                if (productType1!=productType.get()){
                    if (productTypeRequest.getData().getNameProductType().equals(productType1.getNameProductType()))
                        return new Response<>("Type name exists!",0,null);
                }
            }

            productType.get().setNameProductType(productTypeRequest.getData().getNameProductType());
            productType.get().setImageTypeProduct(".image/productType/"+productTypeRequest.getData().getImageTypeProduct());
            productType.get().setUpdateAt(new Date());
            save(productType.get());
            return new Response<>("eidted!",1,productType.get());
        }catch (Exception ex){
            System.out.println("Error:"+ex.getMessage());
            return new Response<>("edit fail",0,null);
        }
    }

    @Override
    public Response<ProductType> delete(int id) {
        try {
            Optional<ProductType> productType = dbContext.productTypeRepository.findById(id);
            if (productType.isEmpty()) return new Response<>("Type Product not found!",0,null);
            for (Product product:dbContext.productRepository.findAll()){
                if (product.getProductType()==productType.get())
                    product.setProductType(null);
            }
            dbContext.productTypeRepository.delete(productType.get());
            return new Response<>("deleted!",1,null);
        }catch (Exception ex){
            System.out.println("Error:"+ex.getMessage());
            return new Response<>("delete fail",0,null);
        }
    }
}
