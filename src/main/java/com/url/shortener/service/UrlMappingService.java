package com.url.shortener.service;

import com.url.shortener.models.ClickEvent;
import com.url.shortener.models.UrlMapping;
import com.url.shortener.models.User;
import com.url.shortener.repository.ClickEventRepository;
import com.url.shortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UrlMappingService {
    @Autowired
    private UrlMappingRepository urlMappingRepository;

    @Autowired
    private ClickEventRepository clickEventRepository;

    public UrlMapping shortenUrl(String originalUrl, User user) {
        String shortUrl = generateShortUrl();
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortUrl(shortUrl);
        mapping.setUser(user);
        mapping.setCreatedDate(LocalDateTime.now());
        return urlMappingRepository.save(mapping);
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

    public UrlMapping getOriginalUrl(String shortUrl) {
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

    public List<UrlMapping> getUrlsByUser(User user) {
        return urlMappingRepository.findByUser(user);
    }
}
