package com.example.demo.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class BookDTO {

    @NotEmpty(message = "Title can`t be empty")
    @Size(min = 2)
    private String title;

    @NotEmpty(message = "ISBN is required")
    @Pattern(regexp = "^(?:ISBN(?:-10)?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$)[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "ISBN has bad format")
    private String isbn;

    @NotEmpty(message = "Author is required")
    private String author;

    @NotEmpty(message = "Book must have number of pages")
    private String page;
}
