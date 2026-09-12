package com.url.shortener.controller;

import com.url.shortener.dtos.ShortenUrlRequest;
import com.url.shortener.dtos.UrlMappingResponse;
import com.url.shortener.models.User;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private final UrlMappingService urlMappingService;
    private final UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingResponse> shortenUrl(@RequestBody ShortenUrlRequest request, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        UrlMappingResponse urlMapping = urlMappingService.createShortenedUrl(request.getOriginalUrl(), user);
        return ResponseEntity.ok(urlMapping);
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMappingResponse>> getUserUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingResponse> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMappingResponse> getUrlAnalytics(@PathVariable String shortUrl) {
        UrlMappingResponse mapping = urlMappingService.getUrlAnalytics(shortUrl);
        if (mapping == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mapping);
    }
}
