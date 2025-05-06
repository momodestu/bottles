package com.example.bottle.service;

import com.example.bottle.entity.Bottle;
import com.example.bottle.mapper.BottleMapper;
import com.example.bottle.vo.BottleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 漂流瓶服务类
 * 处理漂流瓶相关的业务逻辑，包括：
 * 1. 获取随机漂流瓶
 * 2. 创建新漂流瓶
 * 3. 更新漂流瓶被拾取次数
 */
@Service
@RequiredArgsConstructor
public class BottleService {
    private final BottleMapper bottleMapper; // MyBatis-Plus的Mapper接口

    /**
     * 获取随机漂流瓶
     * @return 随机漂流瓶的视图对象，包含漂流瓶内容和用户信息
     */
    public BottleVO getRandomBottle() {
        return bottleMapper.findRandomBottle();
    }

    /**
     * 创建新漂流瓶
     * @param bottle 漂流瓶实体对象
     * @Transactional 注解确保方法在事务中执行
     */
    @Transactional
    public void createBottle(Bottle bottle) {
        bottle.setPickCount(0); // 初始化拾取次数为0
        bottleMapper.insert(bottle); // 插入数据库
    }

    /**
     * 增加漂流瓶的拾取次数
     * @param bottleId 漂流瓶ID
     * @Transactional 注解确保方法在事务中执行
     */
    @Transactional
    public void incrementPickCount(Long bottleId) {
        bottleMapper.incrementPickCount(bottleId); // 更新数据库中的拾取次数
    }
}