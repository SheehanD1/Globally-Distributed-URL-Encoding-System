package com.url.shortener.service;

import com.url.shortener.dtos.UrlMappingResponse;
import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.UrlMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlMappingService {
    private final UrlMappingRepository urlMappingRepository;
    private final ClickEventRepository clickEventRepository;

    public UrlMappingResponse createShortenedUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();
        // Check for collision
        while (urlMappingRepository.findByShortUrl(shortUrl) != null) {
            shortUrl = generateShortUrl();
        }
        
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortUrl(shortUrl);
        mapping.setUser(user);
        mapping.setCreatedDate(LocalDateTime.now());
        UrlMapping saved = urlMappingRepository.save(mapping);
        return convertToResponse(saved);
    }

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder shortUrl = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }

    public UrlMapping resolveAndTrackClick(String shortUrl) {
        UrlMapping mapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (mapping != null) {
            mapping.setClickCount(mapping.getClickCount() + 1);
            urlMappingRepository.save(mapping);

            // Record click event
            ClickEvent clickEvent = new ClickEvent();
            clickEvent.setClickDate(LocalDateTime.now());
            clickEvent.setUrlMapping(mapping);
            clickEventRepository.save(clickEvent);
        }
        return mapping;
    }

    public List<UrlMappingResponse> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user).stream()
                .map(this::convertToResponse)
                .toList();
    }

    public UrlMappingResponse getUrlAnalytics(String shortUrl) {
        UrlMapping mapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (mapping == null) {
            return null;
        }
        return convertToResponse(mapping);
    }

    public UrlMappingResponse convertToResponse(UrlMapping mapping) {
        return UrlMappingResponse.builder()
                .id(mapping.getId())
                .originalUrl(mapping.getOriginalUrl())
                .shortUrl(mapping.getShortUrl())
                .clickCount(mapping.getClickCount())
                .createdDate(mapping.getCreatedDate())
                .username(mapping.getUser() != null ? mapping.getUser().getUsername() : null)
                .build();
    }
}
