package com.example.demo.repository;

import com.example.demo.model.BookEntity;
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
        switch (criteria) {
            case "title":
                return findAllByTitle(searchWord);
            case "author":
                return findAllByAuthor(searchWord);
            case "isbn":
                return findAllByIsbn(searchWord);
            default:
                return findAllBooks();
        }
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
