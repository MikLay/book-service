package com.example.demo.service;

import com.example.demo.model.BookEntity;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BookEntity createBook(BookEntity bookEntity) {
        return bookRepository.saveAndFlush(bookEntity);
    }

    @Transactional
    public BookEntity getBookById(int id) {
        Optional<BookEntity> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null);
    }

    @Transactional
    public List<BookEntity> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public List<BookEntity> findAllByCriteria(String criteria, String searchWord) {
        System.out.println(findAllByTitle(searchWord));
        return switch (criteria) {
            case "title" -> findAllByTitle(searchWord);
            case "author" -> findAllByAuthor(searchWord);
            case "isbn" -> findAllByIsbn(searchWord);
            default -> findAllBooks();
        };
    }

    @Transactional
    public List<BookEntity> findAllByTitle(String title) {
        return bookRepository.findAllByTitle('%' + title + '%');
    }


    @Transactional
    public List<BookEntity> findAllByIsbn(String isbn) {
        return bookRepository.findAllByIsbn('%' + isbn + '%');
    }

    @Transactional
    public List<BookEntity> findAllByAuthor(String author) {
        return bookRepository.findAllByAuthor('%' + author + '%');
    }
}
