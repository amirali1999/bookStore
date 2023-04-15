package com.example.bookStore.model;

import com.example.bookStore.request.BookRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "book",schema = "public",catalog = "bookstore")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "isbn")
    private int isbn;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "year_of_public")
    private int year_of_public;
    @Column(name = "publisher")
    private String publisher;
    @Column(name = "image_url_s")
    private String image_url_s;
    public Book(int isbn, String title, String author, int year_of_public, String publisher, String image_url_s) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.year_of_public = year_of_public;
        this.publisher = publisher;
        this.image_url_s = image_url_s;
    }
    public Book(BookRequest bookRequest){
        this.isbn = bookRequest.getIsbn();
        this.title = bookRequest.getTitle();
        this.author = bookRequest.getAuthor();
        this.year_of_public = bookRequest.getYear_of_public();
        this.publisher = bookRequest.getPublisher();
        this.image_url_s = bookRequest.getImage_url_s();
    }
}
