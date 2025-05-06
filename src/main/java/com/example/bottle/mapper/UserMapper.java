package com.example.bottle.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.bottle.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户数据访问接口
 * 继承MyBatis-Plus的BaseMapper提供基础CRUD操作
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 检查用户名是否存在
     * @param username 要检查的用户名
     * @return 如果用户名已存在返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM users WHERE username = #{username}")
    boolean exists(String username);
    
    /**
     * 检查邮箱是否存在
     * @param email 要检查的邮箱地址
     * @return 如果邮箱已存在返回true，否则返回false
     */
    @Select("SELECT COUNT(*) > 0 FROM users WHERE email = #{email}")
    boolean existsByEmail(String email);
}