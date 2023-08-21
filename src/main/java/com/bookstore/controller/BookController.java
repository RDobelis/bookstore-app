package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Page<Book> books = bookService.getAllBooks(page, size);
        model.addAttribute("books", books.getContent());
        model.addAttribute("totalPages", books.getTotalPages());
        model.addAttribute("currentPage", page);
        return "index";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String name, Model model) {
        try {
            Book book = bookService.addBook(name);
            model.addAttribute("success", "Book added successfully: " + book.getName());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        } catch (OptimisticLockingFailureException e) {
            model.addAttribute("error", "The book was updated by another user. Please try again.");
        }
        return index(model, 0, 50);
    }
}
