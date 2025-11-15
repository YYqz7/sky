package com.sky.mapper;

import com.sky.entity.DishFlavor;

import java.util.List;


public interface DishFlavorMapper {


    /**
     * 批量插入口味列表
     *
     * @param dishFlavors
     */
    void insertDishFlavor(List<DishFlavor> dishFlavors);

    /**
     *
     * @param dishIDs
     */
    void deleteByDishIds(List<Long> dishIDs);

    /**
     * 根据菜品id查询口味
     *
     * @param dishID
     * @return
     */
    List<DishFlavor> selectByDishID(Long dishID);
}
