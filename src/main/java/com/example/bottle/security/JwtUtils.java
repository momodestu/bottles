package com.example.bottle.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT工具类
 * 负责JWT令牌的生成、解析和验证
 */
@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String jwtSecret; // JWT签名密钥，从配置文件中注入

    @Value("${jwt.expiration}")
    private int jwtExpirationMs; // JWT过期时间(毫秒)，从配置文件中注入

    /**
     * 生成JWT签名密钥
     * @return 返回HMAC-SHA512算法的签名密钥
     */
    private SecretKey getSigningKey() {
        // 将配置的密钥字符串转换为字节数组
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        // 使用Keys工具类生成HMAC-SHA512算法的密钥
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 根据用户认证信息生成JWT令牌
     * @param authentication 用户认证信息
     * @return 生成的JWT令牌字符串
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // 设置主题为用户名称
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // 设置过期时间
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // 使用HS512算法签名
                .compact(); // 生成紧凑的JWT字符串
    }

    /**
     * 从JWT令牌中获取用户名
     * @param token JWT令牌
     * @return 用户名
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 设置签名密钥
                .build()
                .parseClaimsJws(token) // 解析令牌
                .getBody()
                .getSubject(); // 获取主题(用户名)
    }

    /**
     * 验证JWT令牌有效性
     * @param authToken JWT令牌
     * @return 验证结果
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken); // 尝试解析令牌
            return true; // 解析成功表示令牌有效
        } catch (SecurityException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage()); // 签名无效
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage()); // 令牌格式错误
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage()); // 令牌已过期
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage()); // 不支持的令牌类型
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage()); // 声明为空
        }

        return false; // 任何异常都返回false
    }
}