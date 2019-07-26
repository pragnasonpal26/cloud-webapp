package com.neu.csye6225.webApplication.controllers;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import com.neu.csye6225.webApplication.entity.Images;
import com.neu.csye6225.webApplication.service.ImagesService;
import com.timgroup.statsd.StatsDClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(BooksController.class);

    @Autowired
    private StatsDClient statsDClient;

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
        logger.info("Find Image: Start");
 	statsDClient.incrementCounter("endpoint.book.image.http.get");
        Optional<Books> singleBook = booksService.getBooks(UUID.fromString(idBook));
        Images image = singleBook.get().getImage();
        logger.info("Find Image: Success!");
        logger.info("Find Image: Stop");
        return imagesService.getImageUrl(image);
    }

    @PostMapping("/books/{idBook}/image")
    @ResponseStatus(HttpStatus.OK)
    public Images postImage(@RequestParam("file") MultipartFile file, @PathVariable String idBook) {
        logger.info("Upload Image: Start");
	statsDClient.incrementCounter("endpoint.book.image.http.post");
        Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
        Images image = new Images();
        image.setUrl(imagesService.upload(file,image));
        singleBook.setImage(image);
        booksService.update(singleBook);
        logger.info("Upload Image: Success");
        logger.info("Upload Image: Stop");
        return image;
    }

    @PutMapping("/books/{idBook}/image/{idImage}")
    public Images updateImage(@PathVariable String idBook, @PathVariable String idImage, @RequestParam("file") MultipartFile file) {
        logger.info("Update Image: Start");
	statsDClient.incrementCounter("endpoint.book.image.http.put");
        Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
        Images image = singleBook.getImage();
        image.setUrl(imagesService.updateImage(file,image));
        imagesService.update(image);
        logger.info("Update Image: Success");
        logger.info("Update Image: Stop");
        return image;
    }

    @DeleteMapping("/books/{idBook}/image/{idImage}")
    public ResponseEntity<String> deleteImage(@PathVariable String idImage, @PathVariable String idBook) {
        logger.info("Delete Image: Start");
	statsDClient.incrementCounter("endpoint.book.image.http.delete");
        Books singleBook = booksService.getBooks(UUID.fromString(idBook)).get();
        Images image = singleBook.getImage();
        imagesService.delete(image);
        singleBook.setImage(null);
        booksService.update(singleBook);
        imagesService.deleteImages(UUID.fromString(idImage));
        logger.info("Delete Image: Success!");
        logger.info("Delete Image: Stop");
        return new ResponseEntity("Deleted successfully!", HttpStatus.OK);
    }
}
