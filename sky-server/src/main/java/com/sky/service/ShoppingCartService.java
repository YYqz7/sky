package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void add2ShoppingCart(ShoppingCartDTO dto);

    List<ShoppingCart> ViewCurrentUserShoppingCart(Long currentId);

    void clear();

    void deleteOne(ShoppingCartDTO dto);
}
