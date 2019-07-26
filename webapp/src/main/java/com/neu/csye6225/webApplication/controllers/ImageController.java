package com.neu.csye6225.webApplication.controllers;


import java.util.List;

import java.util.Optional;
import java.util.UUID;

import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.neu.csye6225.webApplication.entity.Books;
import com.neu.csye6225.webApplication.Utilities.AmazonUtil;
import com.neu.csye6225.webApplication.service.BooksService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class ImageController {

    @Autowired
    private AmazonUtil amazonClient;

    @Autowired
    ImageController(AmazonUtil amazonClient) {
        this.amazonClient = amazonClient;
    }

    @Autowired
    private BooksService booksService;

    @Autowired
    private ImagesService imagesService;

    @GetMapping("/books/{idBook}/image/{idImage}")
    @ResponseStatus(HttpStatus.OK)
    public String getCoverImage(@PathVariable String idBook, @PathVariable String idImage) {
        Optional<Books> singleBook = booksService.getBooks(UUID.fromString(idBook));
        Images image = singleBook.get().getImage();
        return imagesService.getImageUrl(image);
    }

    @PostMapping("/books/{idBook}/image")
    @ResponseStatus(HttpStatus.OK)
    public Images postImage(@RequestParam("file") MultipartFile file, @PathVariable String idBook) {
        Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
        Images image = new Images();
        image.setUrl(imagesService.upload(file,image));
        singleBook.setImage(image);
        booksService.update(singleBook);
        return image;
    }

    @PutMapping("/books/{idBook}/image/{idImage}")
    public Images updateImage(@PathVariable String idBook, @PathVariable String idImage, @RequestParam("file") MultipartFile file) {
        Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
        Images image = singleBook.getImage();
        image.setUrl(imagesService.updateImage(file,image));
        imagesService.update(image);
        return image;
    }

    @DeleteMapping("/books/{idBook}/image/{idImage}")
    public ResponseEntity<String> deleteImage(@PathVariable String idImage, @PathVariable String idBook) {
        Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
        Images image = singleBook.getImage();
        imagesService.delete(image);
        singleBook.setImage(null);
        booksService.update(singleBook);
        imagesService.deleteImages(UUID.fromString(idImage));
        return new ResponseEntity("Deleted successfully!", HttpStatus.OK);
    }
}
