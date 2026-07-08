package com.example.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.product.entity.Product;

public interface ProductService extends IService<Product> {

    /**
     * 恢复库存（取消订单时使用）
     */
    void restoreStock(Long productId, Integer quantity);

    /**
     * 扣减库存
     */
    void deductStock(Long productId, Integer quantity);
}
