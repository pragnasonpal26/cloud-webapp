package com.neu.csye6225.webApplication.service;

import com.neu.csye6225.webApplication.Utilities.AmazonUtil;
import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.exception.FileStorageException;
import com.neu.csye6225.webApplication.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagesService {

    @Autowired
    private Environment env;

    @Autowired
    private AmazonUtil amazonClient;

    @Autowired
    private ImageRepository imageRepository;

    @Value("${localImagePath}")
    private String localImagePath;

    @Transactional
    public List<Images> getImages() {
        return imageRepository.findAll();
    }

    @Transactional
    public Optional<Images> getImage(String id) {
        return imageRepository.findById(id);
    }

    @Transactional
    public Images saveImage(Images images) {
        return imageRepository.save(images);
    }

    @Transactional
    public void update(Images images) {
        if(images != null)
            imageRepository.save(images);
    }

    public String upload(MultipartFile file, Images image) {
        ArrayList<String> acceptedTypes = new ArrayList<String>();
        acceptedTypes.add("image/png");
        acceptedTypes.add("image/jpg");
        acceptedTypes.add("image/jpeg");
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileType = file.getContentType();
        if(fileName.contains(".."))
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        if(!acceptedTypes.contains(fileType))
            throw new FileStorageException("Invalid file type " + fileType);

        String url = "";
        System.out.println(env.getActiveProfiles()[0]);

        if(env.getActiveProfiles()[0].equals("local"))
            url = storeLocal(file);
        else
            url = amazonClient.uploadFile(file);

        System.out.println(url);
        image.setUrl(url);
        update(image);
        return url;
    }

    public void delete(Images image) {
        if(env.getActiveProfiles()[0].equals("local")){
            File fileToDelete = new File(image.getUrl());
            fileToDelete.delete();
        }
        else
            amazonClient.deleteFileFromS3Bucket(image.getUrl());
    }

    @Transactional
    public void deleteImages(UUID fromString) {
        imageRepository.deleteById(fromString);
    }

    public String updateImage(MultipartFile newFile, Images image){
        String newUrl = "";
        if(env.getActiveProfiles()[0].equals("local")){
            File fileToDelete = new File(image.getUrl());
            fileToDelete.delete();
            newUrl = storeLocal(newFile);
        }
        else{
            amazonClient.deleteFileFromS3Bucket(image.getUrl());
            newUrl = amazonClient.uploadFile(newFile);
        }
        return newUrl;
    }

    public String getImageUrl(Images image){
        String url = null;
        if(image.getUrl().contains("http"))
            url = amazonClient.generatePreSignedURL(image.getUrl()).toString();
        else
            url = image.getUrl();
        return url;
    }

    private String storeLocal(MultipartFile file){
        String filePath;
        try{
            filePath = localImagePath + file.getOriginalFilename();

            System.out.println(filePath);

            InputStream inputStream = file.getInputStream();

            File newFile = new File(filePath);
            OutputStream outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            System.out.println("Writing file");
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }catch (IOException ex) {
            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", ex);
        }
        return filePath;
    }
}
