package com.harrison.utils;

import com.harrison.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class JwtUtil {
    // 过期时间为7天
    private static final long EXPIRE_TIME = 60 * 60 * 1000 * 24 * 7;
    // 密钥
    private static final String SECRET = "harri98688045058GcoCZwXXrUMdhOUz8Hikason1109071674958698XXrUMdhOUz8HikariPr688045058GcoCZwXXrUMdhOUz8HikariProxyConnection";
    // 令牌前缀
    private static final String TOKEN_PREFIX = "Bearer ";
    // 存放Token的Header Key
    private static final String HEADER_STRING = "Authorization";

    public static String generateJwt(LoginUser loginUser) {
        if (loginUser == null || loginUser.getId() == null || loginUser.getNickname() == null || loginUser.getHeadPortrait() == null || loginUser.getMail() == null) {
            throw new NullPointerException("loginUser对象中的参数不能为空");
        }
        String UserId = loginUser.getId();
        String nickname = loginUser.getNickname();
        String headPortrait = loginUser.getHeadPortrait();
        String mail = loginUser.getMail();
        String token = Jwts.builder()
                .claim("UserId", UserId)
                .claim("nickname", nickname)
                .claim("headPortrait", headPortrait)
                .claim("mail", mail)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        String jwt = TOKEN_PREFIX + token;
        return jwt;
    }

    public static Claims validateJwtToken(String token) {
        if (token == null || token.length() == 0) {
            log.info("token为空");
            return null;
        }
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET.getBytes())
                    .build()
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
//            return Jwts.parser()
//                    .setSigningKey(SECRET.getBytes())
//                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//                    .getBody();
        } catch (Exception e) {
            log.info("token解析失败:" + e);
            return null;
        }


    }
}
