package com.example.bottle.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bottle.entity.User;
import com.example.bottle.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户详情服务实现类
 * 实现Spring Security的UserDetailsService接口
 * 负责根据用户名加载用户详情信息
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserMapper userMapper;

    /**
     * 根据用户名加载用户详情
     * @param username 用户名
     * @return 用户详情对象
     * @throws UsernameNotFoundException 当用户不存在时抛出异常
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", username)
        );

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        return UserDetailsImpl.build(user);
    }
}