package com.example.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order.entity.Order;

public interface OrderService extends IService<Order> {

    /**
     * 创建订单（含分布式事务）
     */
    Order createOrder(Long userId, Long productId, Integer quantity);
}
