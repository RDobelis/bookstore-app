package com.bookstore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        Optional<Book> existingBook = bookRepository.findByName(name);
        if (existingBook.isPresent()) {
            throw new IllegalArgumentException("A book with this name already exists.");
        }

        Book book = new Book(name, LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAllByOrderByDateAddedDesc();
    }
}
