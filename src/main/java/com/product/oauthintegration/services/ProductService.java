package com.product.oauthintegration.services;

import com.product.oauthintegration.dtos.CreateProductRequest;
import com.product.oauthintegration.dtos.CreateProductResponse;
import com.product.oauthintegration.mappers.ProductMapper;
import com.product.oauthintegration.models.Product;
import com.product.oauthintegration.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CreateProductResponse createProduct(CreateProductRequest request) {
        if(productRepository.existsByName(request.name())){
            throw new RuntimeException("Product name already exists");
        }

        Product product = productMapper.toEntity(request);

        Product savedProduct = productRepository.save(product);

        return  productMapper.toResponse(savedProduct);


    }

    public Page<CreateProductResponse> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable)
                .map(productMapper::toResponse);
    }


}
