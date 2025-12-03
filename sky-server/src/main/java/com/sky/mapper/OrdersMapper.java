package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrderHistoryDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderHistoryVO;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

public interface OrdersMapper {

    /**
     * 根据状态统计订单数量
     *
     * @param status
     */
    @Select("select count(id) from orders where status = #{status}")
    Integer countStatus(Integer status);


    /**
     * 分页条件查询并按下单时间排序
     *
     * @param ordersPageQueryDTO
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    void insert(Orders orders);


    /**
     * 根据订单号和用户id查询订单
     *
     * @param orderNumber
     * @param userId
     */
    @Select("select * from orders where number = #{orderNumber} and user_id= #{userId}")
    Orders getByNumberAndUserId(String orderNumber, Long userId);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    List<OrderHistoryVO> historicalOrderPaginationQuery(Long userID, Integer page, Integer pageSize, Integer status);

    long countHistoricalOrder(Long userID, OrderHistoryDTO orderHistoryDTO);

    OrderHistoryVO selectByOrderID(Long orderID);

    /**
     * 根据订单状态和下单时间查询订单
     *
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);

    /**
     * 营业额数据统计
     *
     * @param end
     * @param begin
     * @param status
     * @return
     */
    Double turnoverStatistics(LocalDateTime begin, LocalDateTime end, Integer status);

    /**
     * 获取每天新用户
     *
     * @param end
     * @param begin
     * @return
     */
    Integer acquireNewUsersEveryDay(LocalDateTime begin, LocalDateTime end);
}
