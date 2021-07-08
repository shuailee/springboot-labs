package com.klein.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @package: com.klein.config
 * @description:
 * @author: klein
 * @date: 2021-07-08 17:21
 **/
@Slf4j
@Configuration
public class JwtTokenUtil {
    /**
     * 秘钥
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 过期时间(秒)
     */
    @Value("${jwt.expire}")
    private long expire;


    /**
     * 生成jwt token
     */
    public String generateToken(String loginName) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                // 头部份
                .setHeaderParam("typ", "JWT")
                // 主题
                .setSubject(loginName)
                // 签发时间
                .setIssuedAt(nowDate)
                // 过期时间
                .setExpiration(expireDate)
                // 验签
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解密token信息
     * */
    public Claims getClaimByToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }

        String[] header = token.split("Bearer");
        token = header[1];
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     *
     * @return true：过期
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
