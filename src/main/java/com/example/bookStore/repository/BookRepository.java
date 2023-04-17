package com.example.bookStore.repository;

import com.example.bookStore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findById(long id);
    Optional<Book> findByTitle(String title);
    Page<List<Book>> findByTitleContains(String title, Pageable pageable);
    Page<List<Book>> findByAuthorContains(String title, Pageable pageable);
    Page<List<Book>> findByPublisherContains(String title, Pageable pageable);
}
