package com.sky.mapper;

import com.sky.entity.OrderDetail;

import java.util.ArrayList;

public interface OrderDetailMapper {
    void insertBatch(ArrayList<OrderDetail> orderDetailList);
}
