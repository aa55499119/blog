package com.example.order.controller;

import com.example.common.dto.BaseResponse;
import com.example.order.entity.Order;
import com.example.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public BaseResponse<Order> getById(@PathVariable Long id) {
        return BaseResponse.success(orderService.getById(id));
    }

    @PostMapping("/create")
    public BaseResponse<Order> createOrder(@RequestParam Long userId,
                                           @RequestParam Long productId,
                                           @RequestParam Integer quantity) {
        return BaseResponse.success(orderService.createOrder(userId, productId, quantity));
    }
}
