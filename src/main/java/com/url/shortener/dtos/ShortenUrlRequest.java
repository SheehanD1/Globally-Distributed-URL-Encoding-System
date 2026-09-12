package com.url.shortener.dtos;

import lombok.Data;

@Data
public class ShortenUrlRequest {
    private String originalUrl;
}
