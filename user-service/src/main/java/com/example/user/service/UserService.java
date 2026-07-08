package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.user.entity.User;

public interface UserService extends IService<User> {

    /**
     * 扣减余额
     */
    void deductBalance(Long userId, Integer amount);
}
