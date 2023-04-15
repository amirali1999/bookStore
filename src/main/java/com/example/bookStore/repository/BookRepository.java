package com.example.bookStore.repository;

import com.example.bookStore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(long id);
    Page<List<Book>> findByTitle(String title, Pageable pageable);
    Page<List<Book>> findByTitle(String title);
    Page<List<Book>> findByAuthor(String author, Pageable pageable);
    Page<List<Book>> findByPublisher(String publisher,Pageable pageable);
}
