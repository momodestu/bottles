package com.example.bottle.controller;

import com.example.bottle.entity.Bottle;
import com.example.bottle.security.UserDetailsImpl;
import com.example.bottle.service.BottleService;
import com.example.bottle.vo.BottleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 * 漂流瓶控制器
 * 提供漂流瓶相关API接口
 */
@RestController
@RequestMapping("/api/bottles")
@RequiredArgsConstructor
public class BottleController {
    private final BottleService bottleService;

    /**
     * 获取随机漂流瓶接口
     * @return 漂流瓶视图对象或错误信息
     */
    @GetMapping("/random")
    public ResponseEntity<?> getRandomBottle() {
        try {
            BottleVO bottle = bottleService.getRandomBottle();
            if (bottle != null) {
                bottleService.incrementPickCount(bottle.getId());
                return ResponseEntity.ok(bottle);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("获取漂流瓶失败：" + e.getMessage());
        }
    }

    /**
     * 创建漂流瓶接口
     * @param bottle 漂流瓶请求体
     * @param authentication 认证信息
     * @return 创建结果
     */
    @PostMapping
    public ResponseEntity<?> createBottle(@RequestBody Bottle bottle, Authentication authentication) {
        try {
            Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            bottle.setUserId(userId);
            bottle.setCreatedAt(LocalDateTime.now());
        bottleService.createBottle(bottle);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("创建漂流瓶失败：" + e.getMessage());
        }
    }
}