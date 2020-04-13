package com.example.demo.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@RequiredArgsConstructor(staticName = "of")
public class SearchDTO {
    private final String searchWord;
    private final String searchType;
}
