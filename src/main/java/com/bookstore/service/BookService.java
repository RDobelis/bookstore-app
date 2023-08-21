package com.bookstore.service;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public Book addBook(String name) {
        if (bookRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("A book with this name already exists.");
        }

        Book book = new Book(name, LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Page<Book> getAllBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("dateAdded")));
        return bookRepository.findAllByOrderByDateAddedDesc(pageable);
    }
}
