package com.corgi.esnews.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NewsItem {
    private String title;
    private String originallink;
    private String link;
    private String description;
    private Date pubDate;
}
