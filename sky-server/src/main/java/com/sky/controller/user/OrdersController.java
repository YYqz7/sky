package com.sky.controller.user;

import com.sky.dto.OrderHistoryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrdersService;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户订单相关接口")
@Slf4j
@RequestMapping("/user/order")
@RestController
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    /**
     * 用户下单
     *
     * @param dto
     * @return
     */
    @ApiOperation("用户下单")
    @PostMapping("/submit")
    public Result<OrderSubmitVO> userPlacesOrder(@RequestBody OrdersSubmitDTO dto) {
        log.info("用户下单: {}", dto);
        OrderSubmitVO vo = ordersService.userPlacesOrder(dto);
        return Result.success(vo);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @ApiOperation("订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = ordersService.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);
        return Result.success(orderPaymentVO);
    }


    /**
     * 历史订单查询
     *
     * @param orderHistoryDTO
     * @return
     */
    @ApiOperation("历史订单查询")
    @GetMapping("/historyOrders")
    public Result<PageResult> historicalOrderInquiry(OrderHistoryDTO orderHistoryDTO) {
        log.info("历史订单查询 DTO: {}", orderHistoryDTO);
        PageResult pageResult = ordersService.historicalOrderInquiry(orderHistoryDTO);
        return Result.success(pageResult);
    }

    @ApiOperation("查询订单详情")
    @GetMapping("/orderDetail/{id}")
    public Result inquireOrderDetails(@PathVariable Long id) {
        return Result.success();
    }
}
