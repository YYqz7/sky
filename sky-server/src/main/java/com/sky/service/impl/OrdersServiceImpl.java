package com.sky.service.impl;

import com.sky.constant.MessageConstant;
import com.sky.context.BaseContext;
import com.sky.dto.OrderHistoryDTO;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.exception.OrderBusinessException;
import com.sky.mapper.*;
import com.sky.result.PageResult;
import com.sky.service.OrdersService;
import com.sky.utils.WeChatPayUtil;
import com.sky.vo.OrderHistoryVO;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrdersServiceImpl implements OrdersService {

    private static final Long PAGINATION_QUERY_RESULT_IS_EMPTY = 0L;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private WeChatPayUtil weChatPayUtil;
    @Autowired
    private OrdersService ordersService;

    @Override
    @Transactional
    public OrderSubmitVO userPlacesOrder(OrdersSubmitDTO dto) {
        AddressBook adrBook = addressBookMapper.getById(dto.getAddressBookId());
        if (adrBook == null) {
            throw new OrderBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        Long userID = BaseContext.getCurrentId();
        User user = userMapper.selectByID(userID);
        if (user == null) {
            throw new OrderBusinessException(MessageConstant.USER_NOT_LOGIN);
        }

        List<ShoppingCart> shoppingCarts = shoppingCartMapper.ViewCurrentUserShoppingCart(userID);
        if (shoppingCarts == null || shoppingCarts.isEmpty()) {
            throw new OrderBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        Orders orders = new Orders();
        BeanUtils.copyProperties(dto, orders);

        orders.setNumber(UUID.randomUUID().toString());
        orders.setStatus(Orders.PENDING_PAYMENT);
        orders.setUserId(userID);
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);
        orders.setPhone(adrBook.getPhone());
        orders.setAddress(adrBook.getDetail());
        orders.setUserName(user.getName());
        orders.setConsignee(adrBook.getConsignee());
        ordersMapper.insert(orders);
        log.info("订单ID: {}", orders.getId());


        ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
        shoppingCarts.forEach(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart, orderDetail, "id");
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        });
        orderDetailMapper.insertBatch(orderDetailList);

        shoppingCartMapper.clear(userID);

        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
//        // 当前登录用户id
//        Long userId = BaseContext.getCurrentId();
//        User user = userMapper.selectByID(userId);
//
//        //调用微信支付接口，生成预支付交易单
//        JSONObject jsonObject = weChatPayUtil.pay(
//                ordersPaymentDTO.getOrderNumber(), //商户订单号
//                new BigDecimal(0.01), //支付金额，单位 元
//                "外卖订单", //商品描述
//                user.getOpenid() //微信用户的openid
//        );
//
//        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
//            throw new OrderBusinessException("该订单已支付");
//        }
//
//        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
//        vo.setPackageStr(jsonObject.getString("package"));
//
//        return vo;
        /**
         * 模拟支付成功 -- 修改订单状态
         */
        ordersService.paySuccess(ordersPaymentDTO.getOrderNumber());

        return new OrderPaymentVO();
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param outTradeNo
     */
    public void paySuccess(String outTradeNo) {
        // 当前登录用户id
        Long userId = BaseContext.getCurrentId();

        // 根据订单号查询当前用户的订单
        Orders ordersDB = ordersMapper.getByNumberAndUserId(outTradeNo, userId);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        ordersMapper.update(orders);
    }

    @Override
    public PageResult historicalOrderInquiry(OrderHistoryDTO orderHistoryDTO) {
        Long userID = BaseContext.getCurrentId();

        long total = ordersMapper.countHistoricalOrder(userID, orderHistoryDTO);
        if (total == PAGINATION_QUERY_RESULT_IS_EMPTY) {
            return new PageResult(PAGINATION_QUERY_RESULT_IS_EMPTY, Collections.emptyList());
        }

        Integer status = orderHistoryDTO.getStatus();
        Integer pageSize = orderHistoryDTO.getPageSize();
        Integer page = (orderHistoryDTO.getPage() - 1) * pageSize;
        List<OrderHistoryVO> orderList = ordersMapper.historicalOrderPaginationQuery(userID, page, pageSize, status);
        orderList.forEach(order -> order.setOrderDetailList(orderDetailMapper.selectByOrderID(order.getId())));

        return new PageResult(total, orderList);
    }

    @Override
    public OrderHistoryVO inquireOrderDetails(Long orderID) {
        OrderHistoryVO vo = ordersMapper.selectByOrderID(orderID);
        vo.setOrderDetailList(orderDetailMapper.selectByOrderID(orderID));
        return vo;
    }
}
