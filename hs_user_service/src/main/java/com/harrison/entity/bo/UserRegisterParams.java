package com.harrison.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterParams {
    /**
     * 邮箱验证码
     */
    private String code;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String headPortrait;

    /**
     * 签名
     */
    private String signature;

    /**
     * 性别;0女1男
     */
    private Boolean gender;

    /**
     * 邮箱
     */
    private String mail;
}
