package com.neu.csye6225.webApplication.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name= "images")
public class Images {
    @Id
    @Column(name = "imageId")
    private String imageId;

    @NotNull
    @Column(name = "bookId")
    private String bookId;

    @NotNull
    @Column(name = "url")
    private String url;

    @NotNull
    @Column(name = "physical_path")
    private String physicalPath;

    public String getId() {
        return imageId;
    }

    public void setId(String id) {
        this.imageId = id;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhysicalPath() {
        return physicalPath;
    }

    public void setPhysicalPath(String physicalPath) {
        this.physicalPath = physicalPath;
    }
}
