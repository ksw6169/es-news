package com.corgi.esnews.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@JsonIgnoreProperties
@Getter
@Setter
public class News {

    private String channel;
    private Date lastBuildDate;
    private int total;
    private int start;
    private int display;

//    @JsonProperty("items")
    private List<NewsItem> items = new ArrayList<>();
}
