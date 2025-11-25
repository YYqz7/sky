package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ShoppingCartMapper {


    ShoppingCart checkUserShoppingCartContainsItem(ShoppingCart shoppingCart);

    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) " +
            " values (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{createTime})")
    void addNewItems2ShoppingCart(ShoppingCart shoppingCart);

    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateQuantityOfExistingProducts(ShoppingCart scWhether);

    List<ShoppingCart> ViewCurrentUserShoppingCart(Long currentId);

    void clear(Long currentId);

    void deleteCur(ShoppingCart curSC);
}
