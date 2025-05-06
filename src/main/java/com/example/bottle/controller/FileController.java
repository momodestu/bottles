package com.example.bottle.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传控制器
 * 提供文件上传相关API接口
 */
@RestController
@RequestMapping("/api")
public class FileController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.access-path}")
    private String accessPath;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // 创建上传目录
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;

            // 保存文件
            Path filePath = Paths.get(uploadDir, filename);
            Files.copy(file.getInputStream(), filePath);

            // 返回文件访问URL
            String fileUrl = accessPath + "/" + filename;
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "文件上传成功");
            response.put("data", fileUrl);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 500);
            response.put("message", "文件上传失败：" + e.getMessage());
            return ResponseEntity.ok(response);
        }
    }
}