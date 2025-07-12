package com.example.dbapp.mapper;

import org.mapstruct.Mapper;
import com.example.dbapp.model.dto.CartItemDto;
import com.example.dbapp.model.entity.CartItem;

import java.util.List;

@Mapper(componentModel = "default")
public interface CartItemMapper {
    CartItemDto toDto(CartItem item);
    List<CartItemDto> toDtos(List<CartItem> item);
    CartItem toEntity(CartItemDto dto);
}
