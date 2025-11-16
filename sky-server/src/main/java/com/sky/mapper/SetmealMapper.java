package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SetmealMapper {

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
}
