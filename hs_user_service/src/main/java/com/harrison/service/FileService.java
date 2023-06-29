package com.harrison.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传用户头像
     *
     * @param files 头像文件
     * @return 头像文件的url
     */
    String uploadUserImg(MultipartFile files);
}
