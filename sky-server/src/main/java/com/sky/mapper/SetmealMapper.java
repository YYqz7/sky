package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SetmealMapper {

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据分类id查询套餐的数量
     *
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 套餐分页查询
     *
     * @param dto
     * @return
     */
    Page<SetmealVO> selectPage(SetmealPageQueryDTO dto);

    /**
     * 添加新套餐_套餐表
     *
     * @param dto
     */
    @AutoFill(OperationType.INSERT)
    void addNewSetmeal2setmeal(Setmeal dto);

    /**
     * 添加新套餐_套餐细节表
     *
     * @param setmealDishes
     */
    void addNewSetmeal2setmealDish(List<SetmealDish> setmealDishes);

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
    Setmeal querySetmealByID(Long id);

    /**
     * 根据id查询套餐详情
     *
     * @param id
     * @return
     */
    List<SetmealDish> querySetmealDishByID(Long id);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteSetmealBatch(List<Long> ids);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteSetmealDishBatch(List<Long> ids);

    /**
     * 修改套餐表
     *
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void modifySetmeal(Setmeal setmeal);
}
