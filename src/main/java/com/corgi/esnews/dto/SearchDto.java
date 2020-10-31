package com.corgi.esnews.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SearchDto {

    public enum SortOption {
        sim,
        date
    }

    private String query;
    private int display;
    private int start;
    private SortOption sort;
}
