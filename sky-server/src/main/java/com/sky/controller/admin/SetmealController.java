package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param dto
     * @return
     */
    @CacheEvict(cacheNames = "setMeal", key = "#dto.categoryId")
    @ApiOperation("新增套餐")
    @PostMapping
    public Result addSetmeal(@RequestBody SetmealDTO dto) {
        log.info("新增套餐: {}", dto);
        setmealService.addNewSetmeal(dto);
        return Result.success();
    }

    /**
     * 修改套餐状态
     *
     * @param status
     * @param id
     * @return
     */
    @CacheEvict(cacheNames = "setMeal", allEntries = true)
    @ApiOperation("修改套餐状态")
    @PostMapping("/status/{status}")
    public Result modifySetmealStatus(@PathVariable Integer status, Long id) {
        log.info("修改套餐状态: {}, {}", status, id);
        setmealService.modifySetmealStatus(status, id);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @ApiOperation("根据id查询套餐")
    @GetMapping("/{id}")
    public Result querySetmealByID(@PathVariable Long id) {
        log.info("根据id查询套餐: {}", id);
        SetmealVO vo = setmealService.querySetmealByID(id);
        return Result.success(vo);
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     * @return
     */
    @CacheEvict(cacheNames = "setMeal", allEntries = true)
    @ApiOperation("批量删除套餐")
    @DeleteMapping
    public Result deleteSetmealBatch(@RequestParam List<Long> ids) {
        log.info("批量删除套餐 id = {}", ids);
        setmealService.deleteSetmealBatch(ids);
        return Result.success();
    }

    /**
     * 修改套餐
     *
     * @param dto
     * @return
     */
    @CacheEvict(cacheNames = "setMeal", allEntries = true)
    @ApiOperation("修改套餐")
    @PutMapping
    public Result modifySetmeal(@RequestBody SetmealDTO dto) {
        log.info("修改套餐: {}", dto);
        setmealService.modifySetmeal(dto);
        return Result.success();
    }
}


