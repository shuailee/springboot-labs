package com.shuailee.springboot.study.api.controller;

import com.shuailee.springboot.study.api.annotation.IgnoreAppSign;
import com.shuailee.model.result.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * @program: springboot-study
 * @description: FileUploadController
 * @author: shuai.li
 * @create: 2020-06-23 16:13
 **/
@Slf4j
@RestController
public class FileUploadController {

    @IgnoreAppSign
    @PostMapping("/fileUpload")
    public DataResult fileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        if (Objects.isNull(file)) {
            return DataResult.fail("上传信息为null");
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            return DataResult.fail("上传文件过大：" + file.getSize());
        }
        // 判断文件是否为图片
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            return DataResult.fail("只允许上传图片");
        }

        System.out.println("upload...");
        return DataResult.ok();
    }


}
