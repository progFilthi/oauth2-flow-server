package com.product.oauthintegration.mappers;

import com.product.oauthintegration.dtos.CreateProductRequest;
import com.product.oauthintegration.dtos.CreateProductResponse;
import com.product.oauthintegration.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    Product toEntity(CreateProductRequest request);

    CreateProductResponse toResponse(Product product);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    void updateFromRequest(CreateProductRequest request, @MappingTarget Product product);
}
