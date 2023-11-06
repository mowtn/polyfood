package com.Lotus.polyFood.Controller;

import com.Lotus.polyFood.Model.Product;
import com.Lotus.polyFood.Service.ProductService;
import com.Lotus.polyFood.payload.Request.ProductRequest;
import com.Lotus.polyFood.payload.Request.Request;
import com.Lotus.polyFood.payload.Response.ProductResponse;
import com.Lotus.polyFood.payload.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("page")
    public Page<Product> page(Pageable pageable){
        return productService.page(pageable);
    }
    @GetMapping("detail/{id}")
    public ProductResponse<Product> getProduct(@PathVariable int id){
        return productService.getProduct(id);
    }
    @GetMapping("search")
    public Page<Product> searchByKeyword(Pageable pageable,@RequestParam(name = "keyword") String keyword){
        return productService.searchByKeyword(pageable,keyword);
    }
    @PostMapping("add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<Product> addProduct(@RequestParam("avatarImageProduct")MultipartFile avatarImageProduct,@RequestBody ProductRequest productRequest) throws IOException {
        return productService.add(productRequest,avatarImageProduct);
    }
    @PostMapping(value = "addAvatar")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> addImageAvatar(@RequestParam("avatarImageProduct")MultipartFile avatarImageProduct,@RequestParam("productId")int productId) throws IOException {
        return productService.addImageAvatar(avatarImageProduct,productId);
    }
    @PutMapping("edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<Product> editProduct(@PathVariable int id,@RequestBody ProductRequest productRequest){
        return productService.edit(id,productRequest);
    }
    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response<Product> deleteProduct(@PathVariable int id){
        return productService.delete(id);
    }
}
