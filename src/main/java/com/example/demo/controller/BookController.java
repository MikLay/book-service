package com.example.demo.controller;

import com.example.demo.model.BookEntity;
import com.example.demo.model.dto.SearchDTO;
import com.example.demo.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }


    @RequestMapping(value = "/search", method = {RequestMethod.POST})
    public ResponseEntity<List<BookEntity>> bookControllerGetAllByTitle(@RequestBody final SearchDTO searchDTO) {
        return ResponseEntity.ok(bookService.findAllByCriteria(searchDTO.getSearchType(), searchDTO.getSearchWord()));
    }

    @ResponseBody
    @GetMapping(value = "/books")
    public List<BookEntity> bookFormControllerGet() {
        return bookService.findAllBooks();
    }

    @PreAuthorize("hasAuthority('VIEW_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<BookEntity> bookFormControllerPost(@RequestBody final BookEntity bookModel) {
        return ResponseEntity.ok(bookService.createBook(bookModel));
    }

    @RequestMapping(value = "/books/{bookId}")
    public ResponseEntity<BookEntity> getData(@PathVariable("bookId") Integer bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

}
