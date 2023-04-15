package com.example.bookStore.controller;

import com.example.bookStore.exception.type.DuplicateFieldException;
import com.example.bookStore.exception.type.EmptyFieldException;
import com.example.bookStore.exception.type.InvalidIdException;
import com.example.bookStore.request.BookRequest;
import com.example.bookStore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "book")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(path = "getAll")
    public ResponseEntity<?> getAllBook(@PathVariable("page") int page){
        return bookService.getAllBook(page).createResponseEntity();
    }

    @GetMapping(path = "getAll/{page}/{title}")
    public ResponseEntity<?> getBooksByTitle(@PathVariable("page") int page,
                                             @PathVariable("title") String title){
        return bookService.getBooksByTitle(page,title).createResponseEntity();
    }

    @GetMapping(path = "getAll/{page}/{author}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable("page") int page,
                                              @PathVariable("author") String author){
        return bookService.getBooksByAuthor(page,author).createResponseEntity();
    }

    @GetMapping(path = "getAll/{page}/{publisher}")
    public ResponseEntity<?> getBooksByPublisher(@PathVariable("page") int page,
                                                 @PathVariable("publisher") String publisher){
        return bookService.getBooksByPublisher(page,publisher).createResponseEntity();
    }

    @PostMapping(path = "add_book")
    public ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest) throws EmptyFieldException,
            DuplicateFieldException {
        return bookService.addBook(bookRequest).createResponseEntity();
    }

    @DeleteMapping(path = "delete_book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") long id) throws InvalidIdException {
        return bookService.deleteBook(id).createResponseEntity();
    }

    @PatchMapping(path = "edit_book/{id}")
    public ResponseEntity<?> patchBook(@PathVariable("id") long id, @RequestBody BookRequest bookRequest)
            throws DuplicateFieldException, InvalidIdException {
        return bookService.patchBook(id, bookRequest).createResponseEntity();
    }
}
