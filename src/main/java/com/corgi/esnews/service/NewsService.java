package com.corgi.esnews.service;

import com.corgi.esnews.dto.NewsDto;
import com.corgi.esnews.es.client.EsManager;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    public void getNews() {

    }

    public boolean createNews(NewsDto newsDto) {
        return EsManager.createIndex("test", null, null);
    }
}
