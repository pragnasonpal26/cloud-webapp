package com.neu.csye6225.webApplication.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name= "images")
public class Images{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "url")
    private String url;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
