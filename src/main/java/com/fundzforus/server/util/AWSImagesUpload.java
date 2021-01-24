package com.fundzforus.server.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

@Component
@Slf4j
public class AWSImagesUpload implements InitializingBean {

    @Value("${s3.bucketName}")
    String s3BucketName;

    @Value("${aws.accessKey}")
    String awsAccessKey;

    @Value("${aws.secretKey}")
    String awsSecretKey;

    AmazonS3 amazonS3 = null;

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileTos3bucket(String bucketName, String key, File file) {
        amazonS3.putObject(new PutObjectRequest(bucketName, key, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String uploadFile(MultipartFile multipartFile, int userId, String fileName, String userType, MediaType mediaType) {
        String fileUrl = "";
        try {
            String hostName = InetAddress.getLocalHost().getHostName().trim();
            File file = convertMultiPartToFile(multipartFile);
            fileUrl = "https://" + s3BucketName + ".s3.amazonaws.com" + "/" + mediaType + "/" + hostName + "/" + userType + "/" + userId + "/" + fileName;
            uploadFileTos3bucket(s3BucketName + "/" + mediaType + "/" + hostName + "/" + userType + "/" + userId, fileName, file);
            file.delete();
            log.info("Upload is completed");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Exception Occurred", e);
        }
        System.out.println(fileUrl);
        return fileUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AWSCredentials credentials = new BasicAWSCredentials(
                awsAccessKey,
                awsSecretKey
        );

        amazonS3 = new AmazonS3Client(credentials);
    }
}
