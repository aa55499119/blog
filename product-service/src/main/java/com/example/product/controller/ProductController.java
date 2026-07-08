package com.example.product.controller;

import com.example.common.dto.BaseResponse;
import com.example.product.entity.Product;
import com.example.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public BaseResponse<Product> getById(@PathVariable Long id) {
        return BaseResponse.success(productService.getById(id));
    }

    @PostMapping
    public BaseResponse<Boolean> create(@RequestBody Product product) {
        return BaseResponse.success(productService.save(product));
    }

    @PostMapping("/deduct")
    public BaseResponse<Void> deductStock(@RequestParam Long productId, @RequestParam Integer quantity) {
        productService.deductStock(productId, quantity);
        return BaseResponse.success();
    }

    @PostMapping("/restore")
    public BaseResponse<Void> restoreStock(@RequestParam Long productId, @RequestParam Integer quantity) {
        productService.restoreStock(productId, quantity);
        return BaseResponse.success();
    }
}
