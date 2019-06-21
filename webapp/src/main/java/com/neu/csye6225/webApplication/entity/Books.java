package com.neu.csye6225.webApplication.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name= "books")
public class Books implements Serializable{

	@Id
	@GeneratedValue(generator = "uuid", strategy = GenerationType.AUTO)
	@Type(type = "uuid-char")
	private UUID id;
	
	@NotNull
	@Size(min=3, max=100)
	@Column(name = "title")
	private String title;

	@NotNull
	@Column(name = "author")
	private String author;

	@NotNull
	@Column(name = "isbn")
	private String isbn;

	@Column(name = "quantity")
	private int quantity;

	@OneToOne(optional = true, cascade=CascadeType.ALL)
	@JoinColumn(name = "idImageAttachment", nullable = true)
	private Images images;

	public Images getImage() {
		return images;
	}

	public void setImage(Images image) {
		this.images = image;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	
	
}
