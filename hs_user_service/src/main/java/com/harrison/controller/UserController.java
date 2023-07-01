package com.harrison.controller;

import com.harrison.entity.User;
import com.harrison.entity.bo.UserRegisterParams;
import com.harrison.enums.BusinessName;
import com.harrison.enums.StatusCodeEnum;
import com.harrison.service.FileService;
import com.harrison.service.UserService;
import com.harrison.utils.JsonData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author harrison
 * @since 2023-06-23
 */
@RestController
@RequestMapping("/api/user/v1/")
public class UserController {
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    /**
     * 上传用户头像
     *
     * @param file 用户头像
     * @return 用户头像地址
     */
    @PostMapping("/upload_head_portrait")
    public JsonData uploadImg(@RequestPart("file") MultipartFile file) {
        String imgUrl = fileService.uploadUserImg(file);
        if (StringUtils.isBlank(imgUrl)) {
            return JsonData.fail(StatusCodeEnum.FILE_UPLOAD_ERROR);
        }
        return JsonData.success(imgUrl);
    }

    @PostMapping("/register")
    public JsonData register(@RequestBody UserRegisterParams userRegisterParams) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterParams, user);
        return userService.register(user, BusinessName.USER_REGISTER, userRegisterParams.getCode());
    }
}
