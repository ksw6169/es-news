package com.corgi.esnews.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewsDto {
    private String id;
    private String writer;
    private String title;
    private String contents;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
