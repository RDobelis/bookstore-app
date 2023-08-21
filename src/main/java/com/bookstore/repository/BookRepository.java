package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Optional<Book> findByName(String name);

    Page<Book> findAllByOrderByDateAddedDesc(Pageable pageable);
}
