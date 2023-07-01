package com.harrison.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginParams {
    /**
     * 邮箱
     */
    private String mail;

    /**
     * 密码
     */
    private String password;
}
