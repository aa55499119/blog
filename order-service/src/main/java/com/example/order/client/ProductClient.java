package com.example.order.client;

import com.example.common.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "product-service", path = "/product")
public interface ProductClient {

    @GetMapping("/{id}")
    BaseResponse<Map<String, Object>> getById(@PathVariable Long id);

    @PostMapping("/deduct")
    BaseResponse<Void> deductStock(@RequestParam Long productId, @RequestParam Integer quantity);
}
