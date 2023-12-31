package com.harrison.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

@Slf4j
public class CommonUtil {
    // 获取IP地址
    public static String getIpAddr(javax.servlet.http.HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String headerName = "x-forwarded-for";
        String ip = request.getHeader(headerName);
        if (null != ip && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个IP才是真实IP,它们按照英文逗号','分割
            if (ip.contains(",")) {
                ip = ip.split(",")[0];
            }
        }
        if (checkIp(ip)) {
            headerName = "Proxy-Client-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "WL-Proxy-Client-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "HTTP_CLIENT_IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "HTTP_X_FORWARDED_FOR";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "X-Real-IP";
            ip = request.getHeader(headerName);
        }
        if (checkIp(ip)) {
            headerName = "remote addr";
            ip = request.getRemoteAddr();
            // 127.0.0.1 ipv4, 0:0:0:0:0:0:0:1 ipv6
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (inet != null) {
                    ip = inet.getHostAddress();
                }
            }
        }
        return ip;
    }

    private static boolean checkIp(String ip) {
        return null == ip || ip.length() == 0 || "unknown".equalsIgnoreCase(ip);
    }

    public static String getRandomCode(int length) {
        String code = "";
        for (int i = 0; i < length; i++) {
            code += (int) (Math.random() * 10);
        }
        return code;
    }

    // 生成UUID
    public static String generateUUID(int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    // 给前端发送自定义信息
    public static void sendJsonMessage(HttpServletResponse response, Object obj) {
        response.setContentType("application/json; charset=utf-8");
        try (ServletOutputStream outputStream = response.getOutputStream();) {
            ObjectMapper objectMapper = new ObjectMapper();
            outputStream.print(objectMapper.writeValueAsString(obj));
            response.flushBuffer();
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
