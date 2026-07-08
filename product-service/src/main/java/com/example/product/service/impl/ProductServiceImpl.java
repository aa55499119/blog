package com.example.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.BusinessException;
import com.example.product.entity.Product;
import com.example.product.mapper.ProductMapper;
import com.example.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public void restoreStock(Long productId, Integer quantity) {
        Product product = getById(productId);
        if (product == null) {
            throw new BusinessException("Product not found");
        }
        lambdaUpdate()
                .eq(Product::getId, productId)
                .setSql("stock = stock + " + quantity)
                .update();
        log.info("Stock restored: productId={}, quantity={}", productId, quantity);
    }

    @Override
    public void deductStock(Long productId, Integer quantity) {
        Product product = getById(productId);
        if (product == null) {
            throw new BusinessException("Product not found");
        }
        if (product.getStock() < quantity) {
            throw new BusinessException("Insufficient stock");
        }
        lambdaUpdate()
                .eq(Product::getId, productId)
                .setSql("stock = stock - " + quantity)
                .update();
    }
}
