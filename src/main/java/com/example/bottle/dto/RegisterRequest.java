package com.example.bottle.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 注册请求数据传输对象
 * 包含用户注册所需的用户名、密码和邮箱字段
 */
@Data
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    private String username; // 用户名

    @NotBlank(message = "密码不能为空")
    private String password; // 密码

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email; // 邮箱地址
}