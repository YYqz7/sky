package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void add2ShoppingCart(ShoppingCartDTO dto) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(dto, shoppingCart);

        shoppingCart.setUserId(BaseContext.getCurrentId());
        ShoppingCart scWhether = shoppingCartMapper.checkUserShoppingCartContainsItem(shoppingCart);
        if (scWhether == null) { // 用户购物车中无该商品, 即添加

            // 补充缺失的属性值，判断是购物车中添加菜品还是套餐
            if (dto.getDishId() != null) {
                // 添加的是菜品
                Dish dish = dishMapper.selectByID(dto.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCart.setImage(dish.getImage());
            } else {
                // 添加的是套餐
                Setmeal setmeal = setmealMapper.querySetmealByID(dto.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCart.setImage(setmeal.getImage());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.addNewItems2ShoppingCart(shoppingCart);
        } else { // 用户购物车中已存在该商品
            scWhether.setNumber(scWhether.getNumber() + 1);
            shoppingCartMapper.updateQuantityOfExistingProducts(scWhether);
        }
    }

    @Override
    public List<ShoppingCart> ViewCurrentUserShoppingCart(Long currentId) {
        return shoppingCartMapper.ViewCurrentUserShoppingCart(currentId);
    }
}
