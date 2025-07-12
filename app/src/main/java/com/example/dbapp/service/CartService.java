package com.example.dbapp.service;

import java.util.List;
import java.math.BigDecimal;
import org.mapstruct.factory.Mappers;

import com.example.dbapp.core.AppDatabase;
import com.example.dbapp.mapper.CartMapper;
import com.example.dbapp.model.dto.CartDto;
import com.example.dbapp.model.entity.Cart;
import com.example.dbapp.model.dto.CartSummary;
import com.example.dbapp.repository.dao.CartDao;


public class CartService {

    private final CartDao cartDao;
    private final CartMapper cartMapper = Mappers.getMapper(CartMapper.class);
    private final CartItemService cartItemService;

    public CartService(AppDatabase database, CartItemService cartItemService) {
        this.cartDao = database.cartDao();;
        this.cartItemService = cartItemService;
    }

    public List<Cart> listAllCarts() {
        return cartDao.getAll();
    }

    public Cart getCartById(long id) {
        return cartDao.getById(id);
    }

    public Cart createCart(Cart cart) {
        long id = cartDao.insert(cart);
        return cartDao.getById(id);
    }

    public void updateCart(Cart cart) {
        cartDao.update(cart);
    }

    public void deleteCart(Cart cart) {
        cartDao.delete(cart);
    }

    public void deleteCartById(long id) {
        cartDao.deleteById(id);
    }

    public CartDto getCart(long id) {
        System.out.println("ID " + id);
        Cart cart = getCartById(id);
        System.out.println("ca " + cart.getId());
        CartDto cartDto = cartMapper.toDto(cart);
        cartDto.setItem(cartItemService.getItems(cartDto.getId()));
        addSummary(cartDto);
        return cartDto;
    }

    private void addSummary(CartDto cartDto) {

        CartSummary summaryDto = cartDto.getSummary();
        if (summaryDto == null) {
            summaryDto = new CartSummary();
        }

        BigDecimal tipPercentage = assignedTip(cartDto);

        BigDecimal total = cartDto.getItem().stream()
                .map(d -> d.getProduct().getPrice().multiply(BigDecimal.valueOf(d.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDiscount = assignedDiscount(cartDto);

        BigDecimal totalTip = total.multiply(tipPercentage);

        //BigDecimal tax = storeSetting.getTaxEnabled() ? BigDecimal.valueOf(storeSetting.getTaxRate()) : BigDecimal.ZERO;
        BigDecimal taxAmount = total.multiply(BigDecimal.valueOf(0)).movePointLeft(2);

        summaryDto.setTaxAmount(taxAmount);
        summaryDto.setSubTotal(total.subtract(taxAmount));
        summaryDto.setTipAmount(totalTip);
        summaryDto.setTotal(total.add(totalTip).subtract(totalDiscount));

        cartDto.setSummary(summaryDto);
    }

    private BigDecimal assignedTip(CartDto cartDto) {
        if (cartDto.getTipAmount() == null ) {
            return BigDecimal.ZERO;
        } else  {
            return cartDto.getTipAmount();
        }
    }

    private BigDecimal assignedDiscount(CartDto cartDto) {
        if (cartDto.getDiscountAmount() == null ) {
            return BigDecimal.ZERO;
        } else  {
            return cartDto.getDiscountAmount();
        }
    }

}
