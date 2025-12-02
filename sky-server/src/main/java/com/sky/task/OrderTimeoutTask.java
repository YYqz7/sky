package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class OrderTimeoutTask {

    @Autowired
    private OrdersMapper ordersMapper;

    /**
     * 处理超时未付款订单
     */
    @Scheduled(cron = "0 * * * * *")
    public void orderTimeoutTask() {
        LocalDateTime now = LocalDateTime.now();
        log.info("定时处理超时订单, 时间: {}", new Date());

        List<Orders> orders = ordersMapper.getByStatusAndOrderTime(Orders.PENDING_PAYMENT, now.plusMinutes(-15));
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时, 自动取消");
                order.setCancelTime(now);
                ordersMapper.update(order);
            }
        }
    }

    /**
     * 处理一直处于 "派送中" 订单
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        LocalDateTime now = LocalDateTime.now();
        log.info("定时处理 \"派送中\" 订单: {}", now);

        List<Orders> orders = ordersMapper.getByStatusAndOrderTime(Orders.DELIVERY_IN_PROGRESS, now.plusHours(-1));
        if (orders != null && !orders.isEmpty()) {
            for (Orders order : orders) {
                order.setStatus(Orders.COMPLETED);
                ordersMapper.update(order);
            }

        }
    }
}
