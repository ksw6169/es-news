package com.corgi.esnews.controller;

import com.corgi.esnews.dto.SearchDto;
import com.corgi.esnews.entity.News;
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

    @RequestMapping(value = "/api/v1/news", method = RequestMethod.GET)
    public News searchNews(SearchDto searchDto) throws Exception {
        return newsService.searchNews(searchDto);
    }

    @RequestMapping(value = "/api/v1/news", method = RequestMethod.POST)
    public ResponseEntity createNews(SearchDto searchDto) {
        newsService.createNews(searchDto);
        return responseSuccess();
    }
}
