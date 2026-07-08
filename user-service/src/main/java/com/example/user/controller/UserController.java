package com.example.user.controller;

import com.example.common.dto.BaseResponse;
import com.example.user.entity.User;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public BaseResponse<User> getById(@PathVariable Long id) {
        return BaseResponse.success(userService.getById(id));
    }

    @PostMapping
    public BaseResponse<Boolean> create(@RequestBody User user) {
        return BaseResponse.success(userService.save(user));
    }

    @PostMapping("/deduct")
    public BaseResponse<Void> deductBalance(@RequestParam Long userId, @RequestParam Integer amount) {
        userService.deductBalance(userId, amount);
        return BaseResponse.success();
    }
}
