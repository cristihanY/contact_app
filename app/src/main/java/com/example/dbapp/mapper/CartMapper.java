package com.example.dbapp.mapper;

import org.mapstruct.Mapper;
import com.example.dbapp.model.dto.CartDto;
import com.example.dbapp.model.entity.Cart;

@Mapper(componentModel = "default")
public interface CartMapper {
    CartDto toDto(Cart cart);

    Cart toEntity(CartDto dto);
}
