package com.example.bookStore.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookRequest {

    private int isbn;

    private String title;

    private String author;

    private int year_of_public;

    private String publisher;

    private String image_url_s;

}
