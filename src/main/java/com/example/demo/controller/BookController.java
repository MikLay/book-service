package com.example.demo.controller;

import com.example.demo.model.BookEntity;
import com.example.demo.model.dto.BookDTO;
import com.example.demo.model.dto.SearchDTO;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;


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
    public ResponseEntity<BookDTO> bookFormControllerPost(@Valid @RequestBody final BookDTO bookModel) {

        BookEntity bookEntity = bookService.createBook(bookModel.getTitle(), bookModel.getIsbn(), bookModel.getAuthor(), bookModel.getPage());
        BookDTO bookDTO = new BookDTO();
        bookDTO.setAuthor(bookEntity.getAuthor());
        bookDTO.setIsbn(bookEntity.getIsbn());
        bookDTO.setPage(bookEntity.getPage());
        bookDTO.setTitle(bookEntity.getTitle());
        return ResponseEntity.ok(bookDTO);
    }

    @RequestMapping(value = "/books/{bookId}")
    public ResponseEntity<BookEntity> getData(@PathVariable("bookId") Integer bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

}
