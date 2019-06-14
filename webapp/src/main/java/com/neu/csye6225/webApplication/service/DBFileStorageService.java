package com.neu.csye6225.webApplication.service;

import com.neu.csye6225.webApplication.entity.DBFile;
import com.neu.csye6225.webApplication.exception.FileStorageException;
import com.neu.csye6225.webApplication.exception.MyFileNotFoundException;
import com.neu.csye6225.webApplication.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public void storeFile(MultipartFile file, String path) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            InputStream inputStream = file.getInputStream();

            File newFile = new File(path + file.getOriginalFilename());
            OutputStream outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            System.out.println("Writing file");
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(String path) {
        System.out.println(path);
        File fileToDelete = new File(path);
        fileToDelete.delete();
    }
}