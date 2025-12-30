package com.ecommerce.mapper;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-30T22:39:44+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Microsoft)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.id( product.getId() );
        productDto.name( product.getName() );
        productDto.description( product.getDescription() );
        productDto.price( product.getPrice() );
        productDto.stockQuantity( product.getStockQuantity() );
        productDto.category( product.getCategory() );
        productDto.imageUrl( product.getImageUrl() );

        return productDto.build();
    }

    @Override
    public Product toEntity(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.id( productDto.getId() );
        product.name( productDto.getName() );
        product.description( productDto.getDescription() );
        product.price( productDto.getPrice() );
        product.stockQuantity( productDto.getStockQuantity() );
        product.category( productDto.getCategory() );
        product.imageUrl( productDto.getImageUrl() );

        return product.build();
    }

    @Override
    public void updateProductFromDto(ProductDto dto, Product product) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            product.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            product.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            product.setDescription( dto.getDescription() );
        }
        if ( dto.getPrice() != null ) {
            product.setPrice( dto.getPrice() );
        }
        if ( dto.getStockQuantity() != null ) {
            product.setStockQuantity( dto.getStockQuantity() );
        }
        if ( dto.getCategory() != null ) {
            product.setCategory( dto.getCategory() );
        }
        if ( dto.getImageUrl() != null ) {
            product.setImageUrl( dto.getImageUrl() );
        }
    }
}
