package com.example.demo.repository;

import com.example.demo.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final EntityManager entityManager;

    @Transactional
    public Book createBook(String title, String isbn, String author, String page) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setAuthor(author);
        book.setPage(page);

        return entityManager.merge(book);
    }

    @Transactional
    public Book getBookById(int id) {
        return entityManager.find(Book.class, id);
    }

    @Transactional
    public List<Book> findAllBooks() {
        return entityManager.createQuery("select a from Book a", Book.class).getResultList();
    }

    @Transactional
    public List<Book> findAllByTitle(String title) {
        return entityManager.createNamedQuery(Book.FIND_BY_TITLE, Book.class)
                .setParameter("title", title)
                .getResultList();
    }
}
