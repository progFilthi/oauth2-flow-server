package com.product.oauthintegration.controllers;

import com.product.oauthintegration.dtos.CreateProductRequest;
import com.product.oauthintegration.dtos.CreateProductResponse;
import com.product.oauthintegration.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request){

        CreateProductResponse response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CreateProductResponse>> getAllProducts(@PageableDefault Pageable pageable){
        Page<CreateProductResponse> response = productService.getAllProducts(pageable);
        return ResponseEntity.ok(response);
    }









}
