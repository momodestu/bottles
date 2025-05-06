package com.example.bottle.vo;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 漂流瓶视图对象
 * 包含漂流瓶信息和关联的用户信息
 */
@Data
public class BottleVO {
    private Long id; // 漂流瓶ID
    private String username; // 发布者用户名
    private String content; // 漂流瓶内容
    private String imageUrl; // 图片URL
    private LocalDateTime createdAt; // 创建时间
    private Integer pickCount; // 被拾取次数
}