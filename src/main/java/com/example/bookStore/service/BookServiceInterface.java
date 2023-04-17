package com.example.bookStore.service;

import com.example.bookStore.exception.type.DuplicateFieldException;
import com.example.bookStore.exception.type.EmptyFieldException;
import com.example.bookStore.exception.type.InvalidIdException;
import com.example.bookStore.request.BookRequest;
import com.example.bookStore.response.Response;

public interface BookServiceInterface {

    public Response getAllBook(int page);

    public Response getBooksByTitle(int page, String title);

    public Response getBooksByAuthor(int page, String author);

    public Response getBooksByPublisher(int page, String publisher);

    public Response addBook(BookRequest bookRequest) throws EmptyFieldException, DuplicateFieldException;

    public Response deleteBook(long id) throws InvalidIdException;

    public Response patchBook(long id, BookRequest bookRequest) throws InvalidIdException, DuplicateFieldException;

}
