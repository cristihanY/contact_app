package com.example.dbapp.service;

import java.util.List;
import org.mapstruct.factory.Mappers;
import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.model.dto.CartItemDto;
import com.example.dbapp.model.entity.CartItem;
import com.example.dbapp.mapper.CartItemMapper;
import com.example.dbapp.repository.dao.CartItemDao;


public class CartItemService {

    private final CartItemDao cartItemDao;
    private final CartItemMapper cartItemMapper = Mappers.getMapper(CartItemMapper.class);
    private final ProductService productService;

    public CartItemService(AppDatabase database, ProductService productService) {
        this.cartItemDao = database.cartItemDao();
        this.productService = productService;
    }

    public List<CartItem> listAllItems() {
        return cartItemDao.getAll();
    }

    public CartItem getItemById(long id) {
        return cartItemDao.getById(id);
    }

    public List<CartItem> getItemsByCartId(long cartId) {
        return cartItemDao.getByCartId(cartId);
    }

    public Integer getTotalQuantityByCartId(long cartId) {
        return cartItemDao.getTotalQuantityByCartId(cartId);
    }

    public CartItem createItem(CartItem item) {
        List<CartItem> existingItems = cartItemDao.getByCartId(item.getCartId());
        for (CartItem existing : existingItems) {
            if (existing.getProductId().equals(item.getProductId())) {
                int newQuantity = existing.getQuantity() + item.getQuantity();
                existing.setQuantity(newQuantity);
                cartItemDao.update(existing);
                return existing;
            }
        }
        long id = cartItemDao.insert(item);
        return cartItemDao.getById(id);
    }


    public List<Long> createItems(List<CartItem> items) {
        return cartItemDao.insertAll(items);
    }

    public void updateItem(CartItem item) {
        cartItemDao.update(item);
    }

    public void updateItemQuantity(Long cartItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            return;
        }
        CartItem cartItem = getItemById(cartItemId);
        cartItem.setQuantity(quantity);
        cartItemDao.update(cartItem);
    }


    public void deleteItem(CartItem item) {
        cartItemDao.delete(item);
    }

    public void deleteItemById(long id) {
        cartItemDao.deleteById(id);
    }

    public void deleteItemsByCartId(long cartId) {
        cartItemDao.deleteByCartId(cartId);
    }

    public List<CartItemDto> getItems(long cartId) {
        List<CartItemDto> cartItemDtos = cartItemMapper.toDtos(getItemsByCartId(cartId));
        for (CartItemDto cartItemDto: cartItemDtos) {
            cartItemDto.setProduct(productService.getById(cartItemDto.getProductId()));
        }
        return cartItemDtos;
    }


    public CartItemDto getItem(long id) {
        CartItemDto cartItemDto = cartItemMapper.toDto(cartItemDao.getById(id));
        cartItemDto.setProduct(productService.getById(cartItemDto.getProductId()));
        return cartItemDto;
    }
}
