package com.example.bookStore.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class BookRequest {

    private String title;

    private String author;

    private int year_of_public;

    private String publisher;
}
