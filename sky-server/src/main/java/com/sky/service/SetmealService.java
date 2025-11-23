package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 条件查询
     *
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     *
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);

    /**
     * 套餐分页查询
     *
     * @param dto
     * @return
     */
    PageResult querySetmaelPage(SetmealPageQueryDTO dto);

    /**
     * 添加新套餐
     *
     * @param dto
     */
    void addNewSetmeal(SetmealDTO dto);

    /**
     * 修改套餐状态
     *
     * @param status
     * @param id
     */
    void modifySetmealStatus(Integer status, Long id);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO querySetmealByID(Long id);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteSetmealBatch(List<Long> ids);

    /**
     * 修改套餐
     *
     * @param dto
     */
    void modifySetmeal(SetmealDTO dto);
}
