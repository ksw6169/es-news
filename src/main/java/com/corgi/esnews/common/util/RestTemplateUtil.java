package com.corgi.esnews.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Set;

public class RestTemplateUtil {

    public static <T> T get(String url, HttpHeaders headers, Map<String, Object> params, Class<T> responseType) throws JsonProcessingException {

        url = toQueryString(url, params);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return new ObjectMapper().readValue(response.getBody(), responseType);
    }

    private static String toQueryString(String url, Map<String, Object> params) {

        StringBuffer strBuf = new StringBuffer();
        strBuf.append(url);
        strBuf.append("?");

        Set<String> keySet = params.keySet();

        for (String key : keySet) {
            strBuf.append(key);
            strBuf.append("=");
            strBuf.append(params.get(key));
            strBuf.append("&");
        }

        strBuf.deleteCharAt(strBuf.length()-1);

        return strBuf.toString();
    }
}
