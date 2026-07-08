package com.example.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.exception.BusinessException;
import com.example.user.entity.User;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void deductBalance(Long userId, Integer amount) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("User not found");
        }
        if (user.getBalance() < amount) {
            throw new BusinessException("Insufficient balance");
        }
        lambdaUpdate()
                .eq(User::getId, userId)
                .setSql("balance = balance - " + amount)
                .update();
    }
}
