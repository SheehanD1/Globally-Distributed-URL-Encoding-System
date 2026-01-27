package com.url.shortener.controller;

import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.service.UrlMappingService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {
    private final UrlMappingService urlMappingService;
    private final UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMapping> shortenUrl(@RequestBody Map<String, String> request, Principal principal) {
        String originalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMapping urlMapping = urlMappingService.shortenUrl(originalUrl, user);
        return ResponseEntity.ok(urlMapping);
    }

    @GetMapping("/myurls")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UrlMapping>> getUserUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMapping> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UrlMapping> getUrlAnalytics(@PathVariable String shortUrl) {
        // Just reusing getOriginalUrl for now to get mapping details, but in reality we might want separate analytics method
        // For MVP, knowing click count from UrlMapping is enough
        UrlMapping mapping = urlMappingService.getOriginalUrl(shortUrl);
        return ResponseEntity.ok(mapping);
    }
}
