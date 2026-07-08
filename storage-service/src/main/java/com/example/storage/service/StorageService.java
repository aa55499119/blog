package com.example.storage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.storage.entity.Storage;

public interface StorageService extends IService<Storage> {

    /**
     * 扣减库存（Seata 分布式事务示例）
     */
    void deduct(Long productId, Integer count);
}
