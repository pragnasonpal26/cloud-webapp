package com.neu.csye6225.webApplication.service;

import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.exception.FileStorageException;
import com.neu.csye6225.webApplication.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImagesService {
    @Autowired
    private ImageRepository imageRepository;

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
        imageRepository.save(images);
    }

    public void storeFile(MultipartFile file, Images image) {
        ArrayList<String> acceptedTypes = new ArrayList<String>();
        acceptedTypes.add("image/png");
        acceptedTypes.add("image/jpg");
        acceptedTypes.add("image/jpeg");
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileType = file.getContentType();
        System.out.println(fileType);
        try {
            if(fileName.contains(".."))
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            if(!acceptedTypes.contains(fileType))
                throw new FileStorageException("Invalid file type " + fileType);

            File oldFile = new File(image.getUrl());
            String newFilePath = oldFile.getParent() +  "/" + file.getOriginalFilename();

            InputStream inputStream = file.getInputStream();

            File newFile = new File(newFilePath);
            OutputStream outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            System.out.println("Writing file");
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            image.setUrl(newFilePath);
            update(image);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(String path) {
        System.out.println(path);
        File fileToDelete = new File(path);
        fileToDelete.delete();
    }

    @Transactional
    public void deleteImages(UUID fromString) {
        imageRepository.deleteById(fromString);
    }
}
