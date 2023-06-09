package com.example.bookStore.service;

import com.example.bookStore.document.Elastic;
import com.example.bookStore.exception.type.DuplicateFieldException;
import com.example.bookStore.exception.type.EmptyFieldException;
import com.example.bookStore.exception.type.InvalidIdException;
import com.example.bookStore.model.Book;
import com.example.bookStore.repository.BookRepository;
import com.example.bookStore.repository.ElasticRepository;
import com.example.bookStore.request.BookRequest;
import com.example.bookStore.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Service
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final MessageSource messageSource;
    private final ElasticRepository elasticRepository;

    public BookService(BookRepository bookRepository, MessageSource messageSource, ElasticRepository elasticRepository) {
        this.bookRepository = bookRepository;
        this.messageSource = messageSource;
        this.elasticRepository = elasticRepository;
    }

    public Response getAllBook(int page){
        Page<Book> pages = bookRepository.findAll(PageRequest.of(page - 1, 10));
        log.info("get all books successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get all books successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    public Response getBooksByTitle(int page, String title){
        Page<List<Book>> pages = bookRepository.findByTitleContains(title, PageRequest.of(page - 1, 10));
        log.info("get all books by title "+title+" successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get all books by title "+title+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    public Response getBooksByAuthor(int page, String author){
        Page<List<Book>> pages = bookRepository.findByAuthorContains(author, PageRequest.of(page - 1, 10));
        log.info("get all books by author "+author+" successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get all books by author "+author+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.book.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    public Response getBooksByPublisher(int page, String publisher){
        Page<List<Book>> pages = bookRepository.findByPublisherContains(
                publisher,
                PageRequest.of(page - 1, 10)
        );
        log.info("get all books by publisher "+publisher+" successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get all books by publisher "+publisher+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    public Response addBook(BookRequest bookRequest) throws EmptyFieldException, DuplicateFieldException {
        if(bookRequest.getTitle().isEmpty()){
            log.error("Title field is empty");
            elasticRepository.save(
                    new Elastic(LocalDateTime.now().toString(),
                            HttpStatus.BAD_REQUEST.toString(),
                            "error",
                            "Title field is empty"
                    )
            );
            throw new EmptyFieldException("Title field is empty");
        }
        if(bookRequest.getAuthor().isEmpty()){
            log.error("Author field is empty");
            elasticRepository.save(
                    new Elastic(LocalDateTime.now().toString(),
                            HttpStatus.BAD_REQUEST.toString(),
                            "error",
                            "Author field is empty"
                    )
            );
            throw new EmptyFieldException("Author field is empty");
        }
        if(bookRepository.findByTitle(bookRequest.getTitle()).isPresent()){
            log.error("Title is duplicate");
            elasticRepository.save(
                    new Elastic(LocalDateTime.now().toString(),
                            HttpStatus.BAD_REQUEST.toString(),
                            "error",
                            "Title is duplicate"
                    )
            );
            throw new DuplicateFieldException("Title is duplicate");
        }
        Book book = new Book(bookRequest);
        bookRepository.save(book);
        log.info("add book successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "add book successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("add.book.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                book,
                1);
    }

    public Response deleteBook(long id) throws InvalidIdException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new InvalidIdException("Id not found"));
        bookRepository.delete(book);
        log.info("delete book "+book.getTitle()+" successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "delete book "+book.getTitle()+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.book.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                book,
                1);
    }
    //TODO fix this FUCKING api
    public Response patchBook(long id, BookRequest bookRequest) throws InvalidIdException, DuplicateFieldException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new InvalidIdException("Id not found"));
        Book new_book = new Book(bookRequest);
        if(new_book.getTitle() != null &&!Objects.equals(book.getTitle(), new_book.getTitle())){
            if(bookRepository.findByTitle(new_book.getTitle()).isPresent()){
                throw new DuplicateFieldException("Title is duplicate");
            }
            book.setTitle(new_book.getTitle());
        }
        if(new_book.getAuthor() != null && !Objects.equals(book.getAuthor(), new_book.getAuthor())){
            book.setAuthor(new_book.getAuthor());
        }
        if(new_book.getPublisher() != null &&!Objects.equals(book.getPublisher(), new_book.getPublisher())){
            book.setPublisher(new_book.getPublisher());
        }
        if(new_book.getYear_of_public() != 0 && !Objects.equals(book.getYear_of_public(), new_book.getYear_of_public())){
            book.setYear_of_public(new_book.getYear_of_public());
        }
        bookRepository.save(book);
        log.info("update book "+book.getTitle()+" successfully");
        elasticRepository.save(
                new Elastic(LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "delete book "+book.getTitle()+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.book.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                new_book,
                1
        );
    }
}
