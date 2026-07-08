package com.example.storage.controller;

import com.example.common.dto.BaseResponse;
import com.example.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping("/deduct")
    public BaseResponse<Void> deduct(@RequestParam Long productId, @RequestParam Integer count) {
        storageService.deduct(productId, count);
        return BaseResponse.success();
    }
}
