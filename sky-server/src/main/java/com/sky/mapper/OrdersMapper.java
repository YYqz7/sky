package com.sky.mapper;

import com.sky.dto.OrderHistoryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderHistoryVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrdersMapper {
    void insert(Orders orders);


    /**
     * 根据订单号和用户id查询订单
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    List<OrderHistoryVO> historicalOrderPaginationQuery(Long userID, Integer page, Integer pageSize, Integer status);

    long countHistoricalOrder(Long userID, OrderHistoryDTO orderHistoryDTO);

    OrderHistoryVO selectByOrderID(Long orderID);
}
