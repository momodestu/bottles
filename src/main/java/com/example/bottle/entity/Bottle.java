package com.example.bottle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 漂流瓶实体类
 * 对应数据库中的bottles表
 */
@Data
@TableName("bottles")
public class Bottle {
    @TableId(type = IdType.AUTO)
    private Long id; // 漂流瓶ID
    private Long userId; // 用户ID
    private String content; // 漂流瓶内容
    private String imageUrl; // 图片URL
    private LocalDateTime createdAt; // 创建时间
    private Integer pickCount; // 被拾取次数
}