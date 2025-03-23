package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * 通用接口
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传接口
     *
     * @param file 文件
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("CommonController: 文件上传, file={}", file);

        try {
            // 获取原始文件名称
            String originalFilename = file.getOriginalFilename();
            // 截取后缀名 image.png
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 创建新的文件名称
            String fileName = UUID.randomUUID().toString() + extension;
            log.info("CommonController: 文件上传, 原始文件名称={}, 新文件名称={}", originalFilename, fileName);

            // 上传文件
//            String filePath = aliOssUtil.upload(file.getBytes(), fileName);
            // todo: 这里使用静态资源图片替代图片上传功能
            String filePath = "http://localhost:8080/imgs/" + originalFilename;
            log.info("CommonController: 文件上传, 原始文件名称={}, 静态资源图片={}", originalFilename, filePath);
            return Result.success(filePath);
        } catch (Exception e) {
            log.error("CommonController: 文件上传失败, exception={}", e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
