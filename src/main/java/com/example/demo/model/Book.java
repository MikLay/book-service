package com.example.demo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book")
@NamedQueries({
        @NamedQuery(query = "select  u from Book u where u.title = :title", name = Book.FIND_BY_TITLE)
})
@RequiredArgsConstructor
@Data
public class Book {

    public static final String FIND_BY_TITLE = "Book.FIND_BY_TITLE";

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "author")
    private String author;

    @Column(name = "PAGE")
    private String page;
}
