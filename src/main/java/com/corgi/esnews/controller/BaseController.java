package com.corgi.esnews.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public abstract class BaseController {

    protected ResponseEntity responseSuccess() {
        return responseSuccess(null);
    }

    protected ResponseEntity responseSuccess(String message) {
        return new ResponseEntity(message, HttpStatus.OK);
    }

    protected ResponseEntity responseError(HttpStatus status) {
        return responseError(null, status);
    }

    protected ResponseEntity responseError(String reason, HttpStatus status) {
        log.error(reason);
        return new ResponseEntity(reason, status);
    }
}
