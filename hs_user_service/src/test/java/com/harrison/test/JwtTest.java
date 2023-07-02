package com.harrison.test;

import com.harrison.model.LoginUser;
import com.harrison.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
public class JwtTest {
    @Test
    public void jwtDecodeTest() {
        String jwt = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJVc2VySWQiOiIxNjc0OTU4Njk4Njg4MDQ1MDU4Iiwibmlja25hbWUiOiLojIPmtIsiLCJoZWFkUG9ydHJhaXQiOiJ2ZW5pYW0gbGFib3JpcyIsIm1haWwiOiJoY19oYXJyaXNvbkBvdXRsb29rLmNvbSIsImV4cCI6MTY4ODg3MDMzMH0.3I26oQGH-6nHFP9uesWEcA96jH5en5tWb8_lh1B-ir2xNbXY6BdeJRE6jPcFb8OB5sxPtNuRG82QO0aFOu5tdw";
        Claims claims = JwtUtil.validateJwtToken(jwt);
        if (claims != null) {
            log.info(claims.toString());
        }
    }

    @Test
    public void createJwt() {
        LoginUser loginUser = new LoginUser("currentUser.getId()", "currentUser.getNickname()", "currentUser.getHeadPortrait()", "currentUser.getMail()");
        String s = JwtUtil.generateJwt(loginUser);
        log.info(s);
    }
}
