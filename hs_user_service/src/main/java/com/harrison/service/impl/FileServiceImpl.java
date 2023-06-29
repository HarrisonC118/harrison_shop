package com.harrison.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.harrison.config.OSSConfig;
import com.harrison.service.FileService;
import com.harrison.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSConfig ossConfig;

    @Override
    public String uploadUserImg(MultipartFile files) {
        // 创建OSSClient实例。
        OSS ossClient = createOSSClient();
        String bucketName = ossConfig.getBucketName();
        // 获取原始文件名
        String originalFilename = files.getOriginalFilename();
        // 获取文件后缀名
        String fileSuffix = null;
        if (originalFilename != null) {
            fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        //获取日期
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 格式化好的日期 当作路径用
        String formatTime = dateTimeFormatter.format(now);
        // 路径 user/2022/02/02/随机UUID.后缀名
        // 生成32位随机字符串
        String randomUUID = CommonUtil.generateUUID();
        // 新文件名
        String newFileName = randomUUID + fileSuffix;
        // 完整路径
        String filePath = "user/" + formatTime + "/" + newFileName;
        try {
            if (ossClient != null) {
                PutObjectResult putObjectResult = ossClient.putObject(bucketName, filePath, files.getInputStream());
                if (putObjectResult != null) {
                    // 返回文件路径
                    return "https://" + bucketName + "." + ossConfig.getEndpoint() + "/" + filePath;
                }
            }
        } catch (IOException e) {
            log.error("上传文件失败:{}", e.getMessage());
        } finally {
            // 关闭OSSClient，释放资源，防止内存泄漏
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    private OSS createOSSClient() {
        DefaultCredentialProvider defaultCredentialProvider = CredentialsProviderFactory.newDefaultCredentialProvider(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(ossConfig.getEndpoint(), defaultCredentialProvider);
    }
}
