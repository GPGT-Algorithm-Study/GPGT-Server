package com.randps.randomdefence.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.cert.CertificateExpiredException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityExistsException;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class randpsExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(Exception e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = CertificateExpiredException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(CertificateExpiredException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "401");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(AccessDeniedException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "403");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
    @ExceptionHandler(value = ArithmeticException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(ArithmeticException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(EntityExistsException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(IllegalArgumentException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "404");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }

    @ExceptionHandler(value = JsonProcessingException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(JsonProcessingException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "500");
        map.put("message", "크롤링 데이터의 HTML을 파싱하는데 실패했습니다.");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }


    @ExceptionHandler(value = InvalidContentTypeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(InvalidContentTypeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", e.getMessage());

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
