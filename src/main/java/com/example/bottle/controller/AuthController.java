package com.example.bottle.controller;

import com.example.bottle.dto.LoginRequest;
import com.example.bottle.dto.RegisterRequest;
import com.example.bottle.security.JwtUtils;
import com.example.bottle.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    /**
     * 用户注册接口
     * @param request 注册请求体，包含用户名、密码和邮箱
     * @return 注册结果响应，成功返回200，失败返回400及错误信息
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            userService.register(request);
            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * 用户登录接口
     * @param request 登录请求体，包含用户名和密码
     * @return 登录结果响应，成功返回JWT令牌，失败返回错误信息
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String jwt = jwtUtils.generateJwtToken(authentication);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("type", "Bearer");
            response.put("username", request.getUsername());
            
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "用户名或密码错误");
            return ResponseEntity.badRequest().body(error);
        } catch (UsernameNotFoundException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "用户不存在");
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "登录失败：" + e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
}