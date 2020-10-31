package com.corgi.esnews.controller;

import com.corgi.esnews.dto.NewsDto;
import com.corgi.esnews.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class NewsController extends BaseController {

    private final NewsService newsService;

    @RequestMapping(value = "/v1/news", method = RequestMethod.GET)
    public void getNews() {

    }

    @RequestMapping(value = "/v1/news", method = RequestMethod.POST)
    public ResponseEntity createNews(NewsDto newsDto) {
        newsService.createNews(newsDto);
        return responseSuccess();
    }






}
