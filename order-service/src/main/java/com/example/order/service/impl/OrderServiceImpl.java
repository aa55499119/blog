package com.example.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.dto.BaseResponse;
import com.example.common.exception.BusinessException;
import com.example.order.client.ProductClient;
import com.example.order.entity.Order;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final ProductClient productClient;

    @Override
    @GlobalTransactional(name = "order-cancel", rollbackFor = Exception.class)
    public Order cancelOrder(Long orderId) {
        Order order = getById(orderId);
        if (order == null) {
            throw new BusinessException("Order not found");
        }
        if (order.getStatus() != 0) {
            throw new BusinessException("Order cannot be cancelled, current status: " + order.getStatus());
        }

        // 1. 恢复商品库存
        BaseResponse<Void> restoreResp = productClient.restoreStock(order.getProductId(), order.getQuantity());
        if (restoreResp.getCode() != 200) {
            throw new BusinessException("Failed to restore stock: " + restoreResp.getMsg());
        }

        // 2. 更新订单状态为已取消（2）
        lambdaUpdate()
                .eq(Order::getId, orderId)
                .set(Order::getStatus, 2)
                .update();

        order.setStatus(2);
        log.info("Order cancelled: id={}, productId={}, quantity={}",
                orderId, order.getProductId(), order.getQuantity());

        return order;
    }

    @Override
    @GlobalTransactional(name = "order-create", rollbackFor = Exception.class)
    public Order createOrder(Long userId, Long productId, Integer quantity) {
        // 1. 查询商品信息
        BaseResponse<Object> productResp = (BaseResponse) productClient.getById(productId);
        if (productResp.getCode() != 200 || productResp.getData() == null) {
            throw new BusinessException("Product not found");
        }

        // 2. 扣减库存（远程调用 product-service）
        BaseResponse<Void> deductResp = productClient.deductStock(productId, quantity);
        if (deductResp.getCode() != 200) {
            throw new BusinessException("Failed to deduct stock: " + deductResp.getMsg());
        }

        // 3. 创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalAmount(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(quantity)));
        order.setStatus(0);
        save(order);

        log.info("Order created: id={}, userId={}, productId={}, quantity={}",
                order.getId(), userId, productId, quantity);

        return order;
    }
}
