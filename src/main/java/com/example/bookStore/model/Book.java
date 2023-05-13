package com.example.bookStore.model;

import com.example.bookStore.request.BookRequest;
import lombok.*;

import javax.persistence.*;

@Table(name = "book")
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
    @Column(name = "title",nullable = false,unique = true)
    private String title;
    @Column(name = "author",nullable = false)
    private String author;
    @Column(name = "year_of_public")
    private int year_of_public;
    @Column(name = "publisher")
    private String publisher;
    public Book(String title, String author, int year_of_public, String publisher) {
        this.title = title;
        this.author = author;
        this.year_of_public = year_of_public;
        this.publisher = publisher;
    }
    public Book(BookRequest bookRequest){
        this.title = bookRequest.getTitle();
        this.author = bookRequest.getAuthor();
        this.year_of_public = bookRequest.getYear_of_public();
        this.publisher = bookRequest.getPublisher();
    }
}
