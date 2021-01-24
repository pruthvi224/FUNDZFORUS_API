package com.fundzforus.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadUtil {

    @Value("${local.images.location}")
    String localImagesLocation;

    @Value("${s3.images.enabled}")
    String s3ImagesEnabled;

    @Autowired
    AWSImagesUpload awsImagesUpload;

    public String saveImages(int userId, String fileName, MultipartFile multipartFile,
                             String userType) throws IOException {

        if (s3ImagesEnabled.equals("true")) {
            return awsImagesUpload.uploadFile(multipartFile, userId, fileName, userType, MediaType.IMAGES);
        } else {
            String uploadDir = localImagesLocation + userId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = multipartFile.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ioe) {
                throw new IOException("Could not save image file: " + fileName, ioe);
            }
            return fileName;
        }

    }
}
