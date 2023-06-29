package com.harrison.controller;

import com.harrison.enums.StatusCodeEnum;
import com.harrison.service.FileService;
import com.harrison.utils.JsonData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/upload_head_portrait")
    public JsonData uploadImg(@RequestPart("file") MultipartFile file) {
        String imgUrl = fileService.uploadUserImg(file);
        if (StringUtils.isBlank(imgUrl)) {
            return JsonData.fail(StatusCodeEnum.FILE_UPLOAD_ERROR);
        }
        return JsonData.success(imgUrl);
    }
}
