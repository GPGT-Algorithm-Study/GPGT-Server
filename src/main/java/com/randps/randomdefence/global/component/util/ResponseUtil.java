package com.randps.randomdefence.global.component.util;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

  static public ResponseEntity<Map<String, String>> toResponse(HttpStatus type, String code, String message) {
    HttpHeaders responseHeaders = new HttpHeaders();
    Map<String, String> map = new HashMap<>();
    map.put("type", type.getReasonPhrase());
    map.put("code", code);
    map.put("message", message);
    return new ResponseEntity<>(map, responseHeaders, type);
  }

}
