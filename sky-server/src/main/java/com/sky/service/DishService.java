package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品
     *
     * @param dto
     */
    void addDish(DishDTO dto);


    /**
     * 菜品分页查询
     *
     * @param dto
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dto);

    /**
     * (批量)删除菜品
     *
     * @param ids
     */
    void deleteDishBatch(List<Long> ids);

    /**
     * 菜品回显
     *
     * @param id
     * @return
     */
    DishVO getDishById(Long id);

    /**
     * 修改菜品
     *
     * @param dto
     */
    void updateDish(DishDTO dto);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Dish> getDishesByID(Long categoryId);

    /**
     * 修改菜品状态
     *
     * @param status
     */
    void modifyDishStatus(Long status, Long id);

    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);
}
