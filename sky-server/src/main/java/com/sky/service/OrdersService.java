package com.sky.service;

import com.sky.dto.*;
import com.sky.result.PageResult;
import com.sky.vo.*;

public interface OrdersService {

    /**
     * 完成订单
     *
     * @param id
     */
    void complete(Long id);

    /**
     * 派送订单
     *
     * @param id
     */
    void delivery(Long id);

    /**
     * 商家取消订单
     *
     * @param ordersCancelDTO
     */
    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 接单
     *
     * @param ordersConfirmDTO
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 查询订单详情
     *
     * @param id
     * @return
     */
    OrderVO details(Long id);

    /**
     * 各个状态的订单数量统计
     *
     * @return
     */
    OrderStatisticsVO statistics();


    OrderSubmitVO userPlacesOrder(OrdersSubmitDTO dto);


    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    void paySuccess(String outTradeNo);

    PageResult historicalOrderInquiry(OrderHistoryDTO orderHistoryDTO);

    OrderHistoryVO inquireOrderDetails(Long orderID);

    /**
     * 用户取消订单
     *
     * @param id
     */
    void userCancelById(Long id) throws Exception;


    /**
     * 再来一单
     *
     * @param id
     */
    void repetition(Long id);

    /**
     * 条件搜索订单
     *
     * @param ordersPageQueryDTO
     * @return
     */
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 用户催单
     *
     * @param id
     */
    void reminder(Long id);
}
