package com.bookstore.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;

@Controller
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "index";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String name, Model model) {
        try {
            Book book = bookService.addBook(name);
            model.addAttribute("Success", "Book added successfully: " + book.getName());
        } catch (IllegalArgumentException e) {
            model.addAttribute("Error", e.getMessage());
        }
        return index(model);
    }
}
