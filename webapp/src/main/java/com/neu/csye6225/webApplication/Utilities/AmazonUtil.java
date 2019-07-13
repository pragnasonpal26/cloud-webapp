package com.neu.csye6225.webApplication.Utilities;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

@Service
public class AmazonUtil {

    private AmazonS3 s3client;

    //@Value("${regionName}")
    //private String regionName;
    @Value("${prefixUrl}")
    private String prefixUrl;
    @Value("${bucketName}")
    private String bucketName;
    //@Value("${accessKey}")
    //private String accessKey;
    //@Value("${secretKey}")
    //private String secretKey;
    @PostConstruct
    private void initializeAmazon() {
        s3client = AmazonS3ClientBuilder.defaultClient();
    }


    public String uploadFile(MultipartFile multipartFile) {
        String fileName = "";
        //URL url = null;
        try {
            File file = convertMultiPartToFile(multipartFile);
            fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "https://" + bucketName + "." + prefixUrl + "/" + fileName;
        return url;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        s3client.putObject(request);
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        File file = new File(fileUrl);
        String fileName = file.getName();
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }

    public URL generatePreSignedURL(String filePath){
        File file = new File(filePath);
        String fileName = file.getName();
        System.out.println(fileName);
        java.util.Date expiration = new java.util.Date();
        long milliSeconds = expiration.getTime();
        milliSeconds += 1000 * 60 * 2; // 2 mins
        expiration.setTime(milliSeconds);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName);
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.setExpiration(expiration);

        return s3client.generatePresignedUrl(generatePresignedUrlRequest);
    }
}

