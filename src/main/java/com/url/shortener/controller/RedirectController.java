package com.url.shortener.controller;

import com.url.shortener.models.UrlMapping;
import com.url.shortener.service.UrlMappingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RedirectController {

    private final UrlMappingService urlMappingService;

    @GetMapping("/{shortUrl:[a-zA-Z0-9]{8}}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        UrlMapping mapping = urlMappingService.getOriginalUrl(shortUrl);
        if (mapping != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", mapping.getOriginalUrl());
            return ResponseEntity.status(302).headers(headers).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
