package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 套餐分页查询
     *
     * @param dto
     * @return
     */
    @Override
    public PageResult querySetmaelPage(SetmealPageQueryDTO dto) {
        PageHelper.startPage(dto.getPage(), dto.getPageSize());
        Page<SetmealVO> pg = setmealMapper.selectPage(dto);
        return new PageResult(pg.getTotal(), pg.getResult());
    }

    /**
     * 添加新套餐
     *
     * @param dto
     */
    @Override
    @Transactional
    public void addNewSetmeal(SetmealDTO dto) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        setmealMapper.addNewSetmeal2setmeal(setmeal);
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
        setmealMapper.addNewSetmeal2setmealDish(dto.getSetmealDishes());
    }

    /**
     * 修改套餐状态
     *
     * @param status
     * @param id
     */
    @Override
    public void modifySetmealStatus(Integer status, Long id) {
        setmealMapper.modifySetmealStatus(status, id);
    }

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO querySetmealByID(Long id) {
        Setmeal setmeal = setmealMapper.querySetmealByID(id);
        List<SetmealDish> setmealDish = setmealMapper.querySetmealDishByID(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDish);
        return setmealVO;
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteSetmealBatch(List<Long> ids) {
        setmealMapper.deleteSetmealBatch(ids);
        setmealMapper.deleteSetmealDishBatch(ids);
    }

    /**
     * 修改套餐
     *
     * @param dto
     */
    @Override
    @Transactional
    public void modifySetmeal(SetmealDTO dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("套餐信息或ID不能为空");
        }

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        setmealMapper.modifySetmeal(setmeal);

        List<Long> ids = new ArrayList<>();
        ids.add(setmeal.getId());
        setmealMapper.deleteSetmealDishBatch(ids);

        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
            setmealMapper.addNewSetmeal2setmealDish(setmealDishes);
        }
    }
}
