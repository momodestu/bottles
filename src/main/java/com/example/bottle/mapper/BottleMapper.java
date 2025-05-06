package com.example.bottle.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bottle.entity.Bottle;
import com.example.bottle.vo.BottleVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 漂流瓶数据访问接口
 * 继承MyBatis-Plus的BaseMapper提供基础CRUD操作
 */
@Mapper
public interface BottleMapper extends BaseMapper<Bottle> {
    /**
     * 插入漂流瓶记录
     * @param bottle 漂流瓶实体对象
     * @return 影响的行数
     */
    @Insert("INSERT INTO bottles (user_id, content, image_url, created_at, pick_count) " +
            "VALUES (#{userId}, #{content}, #{imageUrl}, #{createdAt}, #{pickCount})")
    int insert(Bottle bottle);

    /**
     * 随机获取一个漂流瓶
     * @return 漂流瓶视图对象(包含用户信息)
     */
    @Select("SELECT b.*, u.username FROM bottles b " +
            "JOIN users u ON b.user_id = u.id " +
            "ORDER BY RAND() LIMIT 1")
    BottleVO findRandomBottle();

    /**
     * 增加漂流瓶的拾取次数
     * @param id 漂流瓶ID
     */
    @Update("UPDATE bottles SET pick_count = pick_count + 1 WHERE id = #{id}")
    void incrementPickCount(Long id);
}