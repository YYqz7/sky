package com.sky.mapper;

import com.sky.entity.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public interface OrderDetailMapper {
    void insertBatch(ArrayList<OrderDetail> orderDetailList);

    List<OrderDetail> selectByOrderID(Long id);
}
