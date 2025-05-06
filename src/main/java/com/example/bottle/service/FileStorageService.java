package com.example.bottle.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 文件存储服务类
 * 负责处理文件上传和存储相关逻辑
 */
@Service
public class FileStorageService {
    private final Path fileStorageLocation; // 文件存储路径

    /**
     * 构造函数
     * @param uploadDir 从配置文件中注入的文件上传目录
     * @throws RuntimeException 如果无法创建目录则抛出异常
     */
    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        // 将上传目录转换为绝对路径并规范化
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            // 创建文件存储目录
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * 存储上传的文件
     * @param file 上传的文件对象
     * @return 文件访问路径
     * @throws RuntimeException 如果文件路径非法或存储失败则抛出异常
     */
    public String storeFile(MultipartFile file) {
        // 清理原始文件名中的路径信息
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        // 获取文件扩展名
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        // 生成随机文件名
        String fileName = UUID.randomUUID() + "." + fileExtension;

        try {
            // 检查文件名是否包含非法路径序列
            if (fileName.contains("..")) {
                throw new RuntimeException("Invalid file path sequence " + fileName);
            }

            // 解析目标存储路径
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // 将文件流复制到目标位置
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 返回文件访问路径
            return "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}