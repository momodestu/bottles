package com.example.bottle.service;

import com.example.bottle.dto.RegisterRequest;
import com.example.bottle.entity.User;
import com.example.bottle.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务类
 * 处理用户注册、登录等业务逻辑
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        // 检查邮箱是否已存在
        if (userMapper.existsByEmail(request.getEmail())) {
            throw new RuntimeException("邮箱已被注册");
        }
        
        // 检查用户名是否已存在
        if (userMapper.exists(request.getUsername())) {
            throw new RuntimeException("用户名已存在");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());

        try {
            userMapper.insert(user);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("邮箱已被注册");
        }
    }
}