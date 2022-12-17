package com.gaoge.controller;

import com.gaoge.common.Result;
import com.gaoge.entity.FileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 高歌
 * @Description 文件上传
 * @Date 2021-08-24
 */
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FilesUploadController {
    @Value("${file.location.directory}")
    private String fileDirectory;

    //    文件上传
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String tail = originalFilename.substring(originalFilename.lastIndexOf("."));
//        创建目录
            String header = UUID.randomUUID().toString().replaceAll("-", "");
            String newFileName = header + tail;
            String targetFileLocation = fileDirectory;
            File file1 = new File(targetFileLocation);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            //创建文件
            String targetFileName = targetFileLocation + File.separator + newFileName;
            File targetFile = new File(targetFileName);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            file.transferTo(targetFile);

            String accessAddress = "http://82.157.42.25/" + newFileName;
            FileInfo fileInfo = new FileInfo();
            fileInfo.setCurrentName(newFileName);
            fileInfo.setOriginalName(originalFilename);
            fileInfo.setType(tail);
            fileInfo.setAccessAddress(accessAddress);
            return Result.ok(fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failed("文件上传失败，请重试");
        }

    }
}
