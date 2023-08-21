package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BookControllerTest {

    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService);
    }

    @Test
    void testIndex() {
        List<Book> books = Arrays.asList(new Book("Book1", null), new Book("Book2", null));
        Page<Book> page = new PageImpl<>(books);
        when(bookService.getAllBooks(0, 50)).thenReturn(page);

        String viewName = bookController.index(model, 0, 50);

        assertEquals("index", viewName);
        verify(model).addAttribute("books", books);
        verify(model).addAttribute("totalPages", 1);
        verify(model).addAttribute("currentPage", 0);
    }

    @Test
    void testAddBook_Success() {
        String bookName = "Book1";
        Book book = new Book(bookName, null);
        List<Book> books = Collections.emptyList();
        Page<Book> page = new PageImpl<>(books);
        when(bookService.addBook(bookName)).thenReturn(book);
        when(bookService.getAllBooks(0, 50)).thenReturn(page);

        String viewName = bookController.addBook(bookName, model);

        assertEquals("index", viewName);
        verify(model).addAttribute("success", "Book added successfully: " + bookName);
    }

    @Test
    void testAddBook_BookAlreadyExists() {
        String bookName = "Book2";
        List<Book> books = Collections.emptyList();
        Page<Book> page = new PageImpl<>(books);
        when(bookService.addBook(bookName))
                .thenThrow(new IllegalArgumentException("Book with this name already exists"));
        when(bookService.getAllBooks(0, 50)).thenReturn(page);

        String viewName = bookController.addBook(bookName, model);

        assertEquals("index", viewName);
        verify(model).addAttribute("error", "Book with this name already exists");
    }

    @Test
    void testAddBook_OptimisticLockingFailure() {
        String bookName = "Book3";
        List<Book> books = Collections.emptyList();
        Page<Book> page = new PageImpl<>(books);
        when(bookService.addBook(bookName))
                .thenThrow(new OptimisticLockingFailureException("The book was updated by another user"));
        when(bookService.getAllBooks(0, 50)).thenReturn(page);

        String viewName = bookController.addBook(bookName, model);

        assertEquals("index", viewName);
        verify(model).addAttribute("error", "The book was updated by another user. Please try again.");
    }
}
