package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    public void testAddBookWithExistingName() {
        String bookName = "Existing Book";
        when(bookRepository.findByName(bookName)).thenReturn(Optional.of(new Book()));

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook(bookName);
        });
    }

    @Test
    public void testAddBookSuccessfully() {
        String bookName = "New Book";
        LocalDateTime now = LocalDateTime.now();
        Book book = new Book(bookName, now);
        when(bookRepository.findByName(bookName)).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book savedBook = bookService.addBook(bookName);

        assertEquals(bookName, savedBook.getName());
        assertNotNull(savedBook.getDateAdded());
    }

    @Test
    public void testGetAllBooks() {
        Book book = new Book("Test Book", LocalDateTime.now());
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(book));

        when(bookRepository.findAllByOrderByDateAddedDesc(any(Pageable.class))).thenReturn(bookPage);

        Page<Book> result = bookService.getAllBooks(0, 5);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        assertEquals(book.getName(), result.getContent().get(0).getName());
    }

    @Test
    public void testAddBookWithEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook("");
        });
    }

    @Test
    public void testAddBookWithNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook(null);
        });
    }

    @Test
    public void testGetAllBooksWithInvalidPageNumber() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getAllBooks(-1, 5);
        });
    }

    @Test
    public void testGetAllBooksWithInvalidSize() {
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getAllBooks(0, -5);
        });
    }

    @Test
    public void testGetAllBooksWhenNoneExist() {
        Page<Book> emptyPage = new PageImpl<>(Collections.emptyList());
        when(bookRepository.findAllByOrderByDateAddedDesc(any(Pageable.class))).thenReturn(emptyPage);

        Page<Book> result = bookService.getAllBooks(0, 5);

        assertTrue(result.isEmpty());
    }
}
