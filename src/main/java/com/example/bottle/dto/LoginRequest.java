package com.example.bottle.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录请求数据传输对象
 * 包含用户登录所需的用户名和密码字段
 */
@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}