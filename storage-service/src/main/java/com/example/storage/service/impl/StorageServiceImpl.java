package com.example.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.BusinessException;
import com.example.storage.entity.Storage;
import com.example.storage.service.StorageService;
import org.springframework.stereotype.Service;

@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Override
    public void deduct(Long productId, Integer count) {
        Storage storage = lambdaQuery().eq(Storage::getProductId, productId).one();
        if (storage == null) {
            throw new BusinessException("Storage not found for product: " + productId);
        }
        if (storage.getResidueStock() < count) {
            throw new BusinessException("Insufficient warehouse stock");
        }
        lambdaUpdate()
                .eq(Storage::getProductId, productId)
                .setSql("used_stock = used_stock + " + count +
                        ", residue_stock = residue_stock - " + count)
                .update();
    }
}
