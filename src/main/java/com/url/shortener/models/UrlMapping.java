package com.url.shortener.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "url_mapping",
        uniqueConstraints = {
            @UniqueConstraint(columnNames = "short_url")
        })
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, unique = true, length = 30)
    private String shortUrl;

    private int clickCount = 0;
    private LocalDateTime createdDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "urlMapping")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<ClickEvent> clickEvents;
}