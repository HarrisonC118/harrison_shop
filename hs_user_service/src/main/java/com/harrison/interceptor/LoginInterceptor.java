package com.harrison.interceptor;

import com.harrison.enums.StatusCodeEnum;
import com.harrison.model.LoginUser;
import com.harrison.utils.CommonUtil;
import com.harrison.utils.JsonData;
import com.harrison.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    public static ThreadLocal<LoginUser> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("token");
        if (StringUtils.isBlank(accessToken)) {
            accessToken = request.getParameter("token");
        }
        if (StringUtils.isNotBlank(accessToken)) {
            // 判断token是否合法
            Claims claims = JwtUtil.validateJwtToken(accessToken);
            if (claims != null) {
                log.info("token校验成功");
                String userId = claims.get("UserId").toString();
                String nickname = claims.get("nickname").toString();
                String headPortrait = claims.get("headPortrait").toString();
                String mail = claims.get("mail").toString();
                LoginUser loginUser = new LoginUser();
                loginUser.setId(userId);
                loginUser.setNickname(nickname);
                loginUser.setHeadPortrait(headPortrait);
                loginUser.setMail(mail);
                // 将loginUser对象存入threadLocal中
                threadLocal.set(loginUser);
                return true;
            }
        }
        CommonUtil.sendJsonMessage(response, JsonData.fail(StatusCodeEnum.ACCOUNT_NOT_LOGIN));
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
