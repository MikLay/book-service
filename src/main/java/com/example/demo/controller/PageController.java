package com.example.demo.controller;

import com.example.demo.model.BookEntity;
import com.example.demo.repository.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    private final BookService bookService;

    public PageController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/book/{bookId}")
    public String getData(@PathVariable("bookId") Integer bookId, Model model) {
        BookEntity book = bookService.getBookById(bookId);
        model.addAttribute("book", book);
        return "book-details";
    }
}
