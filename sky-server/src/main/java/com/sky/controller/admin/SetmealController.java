package com.sky.controller.admin;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "套餐管理相关接口")
@RequestMapping("/admin/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;


    /**
     * 套餐分页查询
     *
     * @param dto
     * @return
     */
    @ApiOperation("套餐分页查询")
    @GetMapping("/page")
    public Result getSetmealPage(SetmealPageQueryDTO dto) {
        log.info("套餐分页查询: {}", dto);
        PageResult pg = setmealService.querySetmaelPage(dto);
        return Result.success(pg);
    }

    /**
     * 新增套餐
     *
     * @param setmeal
     * @return
     */
    @ApiOperation("新增套餐")
    @PostMapping
    public Result addSetmeal(@RequestBody Setmeal setmeal) {
        log.info("新增套餐: {}", setmeal);
        return Result.success();
    }
}

