package com.harrison.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方便JWTUtil中的方法使用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private String id;
    private String nickname;
    private String headPortrait;
    private String mail;
}
