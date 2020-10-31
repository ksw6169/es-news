package com.corgi.esnews.service;

import com.corgi.esnews.common.util.ReflectionUtil;
import com.corgi.esnews.common.util.RestTemplateUtil;
import com.corgi.esnews.dto.SearchDto;
import com.corgi.esnews.entity.News;
import com.corgi.esnews.es.client.EsManager;
import com.corgi.esnews.properties.NaverConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class NewsService {

    private final NaverConfiguration naverConfiguration;

    public News searchNews(SearchDto searchDto) throws IllegalAccessException, JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverConfiguration.getClientId());
        headers.set("X-Naver-Client-Secret", naverConfiguration.getClientSecret());

        Map<String, Object> params = ReflectionUtil.toMap(searchDto);

        return RestTemplateUtil.get(naverConfiguration.getRequestUrl(), headers, params, News.class);
    }

    public void createNews(SearchDto searchDto) {
    }





}
