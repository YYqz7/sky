package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "购物车相关接口")
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @ApiOperation("添加到购物车")
    @PostMapping("/add")
    public Result add2ShoppingCart(@RequestBody ShoppingCartDTO dto) {
        log.info("添加购物车: {}", dto);
        shoppingCartService.add2ShoppingCart(dto);
        return Result.success();
    }

    @ApiOperation("查看用户购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> ViewCurrentUserShoppingCart() {
        log.info("查看当前用户购物车user_id = {}", BaseContext.getCurrentId());
        List<ShoppingCart> sc = shoppingCartService.ViewCurrentUserShoppingCart(BaseContext.getCurrentId());
        return Result.success(sc);
    }
}
