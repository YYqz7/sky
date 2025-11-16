package com.sky.service;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

public interface SetmealService {

    /**
     * 套餐分页查询
     *
     * @param dto
     * @return
     */
    PageResult querySetmaelPage(SetmealPageQueryDTO dto);
}
