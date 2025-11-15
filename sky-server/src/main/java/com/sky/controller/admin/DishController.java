package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "菜品相关接口")
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     *
     * @param dto
     * @return
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result<String> addDish(@RequestBody DishDTO dto) {
        log.info("新增菜品: {}", dto);
        dishService.addDish(dto);
        return Result.success();
    }

    /**
     * 菜品分页查询
     *
     * @param dto
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> pageQuery(DishPageQueryDTO dto) {
        log.info("菜品分页查询: {}", dto);
        PageResult pageResult = dishService.pageQuery(dto);
        return Result.success(pageResult);
    }

    /**
     * (批量)删除
     *
     * @param ids
     * @return
     */
    @ApiOperation("(批量)删除菜品")
    @DeleteMapping
    public Result<String> deleteDishBatch(@RequestParam List<Long> ids) {
        log.info("删除菜品, ids: {}", ids);
        dishService.deleteDishBatch(ids);
        return Result.success();
    }


    /**
     * 回显菜品
     *
     * @param id
     */
    @ApiOperation("回显菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        log.info("回显菜品, id = {}", id);
        DishVO vo = dishService.getDishById(id);
        return Result.success(vo);
    }
}
