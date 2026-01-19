# URL Shortener - Spring Boot

A Spring Boot application for creating and managing shortened URLs with click tracking and user authentication capabilities.

## Project Overview

This is a globally-distributed URL shortening system built with Spring Boot that allows users to:
- Create shortened URLs from long URLs
- Track click events on shortened URLs
- Manage user accounts and authentication
- Monitor URL performance through click analytics

## Tech Stack

- **Framework**: Spring Boot 3.4.0
- **Language**: Java 23
- **Build Tool**: Maven
- **ORM**: Spring Data JPA
- **Database**: JPA-compatible database (configurable)
- **Utilities**: Lombok for reducing boilerplate code

## Project Structure

```
src/main/java/com/url/shortener/
├── models/
│   ├── User.java              # User entity with authentication role
│   ├── UrlMapping.java        # Shortened URL mapping with click tracking
│   └── ClickEvent.java        # Click event tracking for analytics
└── UrlShortenerSbApplication.java  # Main Spring Boot application

src/main/resources/
├── application.properties      # Application configuration
├── static/                     # Static web resources
└── templates/                  # Thymeleaf templates (if applicable)
```

## Key Features

### User Management
- User registration and authentication
- Role-based access control (default: ROLE_USER)
- Email-based user identification

### URL Shortening
- Convert long URLs to short codes
- Track original URL and short URL mapping
- Monitor click count for each shortened URL
- Track creation date for each URL

### Analytics
- Click event tracking with timestamps
- Click count aggregation per shortened URL
- User-specific URL management

## Getting Started

### Prerequisites

- Java 23 or higher
- Maven 3.6.0 or higher
- A database (MySQL, PostgreSQL, H2, etc.)

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd url-shortener-sb
```

2. Configure your database in `src/main/resources/application.properties`

3. Build the project:
```bash
mvn clean build
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on the default Spring Boot port (8080).

## Database Schema

### Users Table
- `id`: Long (Primary Key)
- `email`: String
- `username`: String
- `password`: String
- `role`: String (default: ROLE_USER)

### URL Mappings Table
- `id`: Long (Primary Key)
- `original_url`: String
- `short_url`: String
- `click_count`: Integer (default: 0)
- `created_date`: LocalDateTime
- `user_id`: Long (Foreign Key to Users)

### Click Events Table
- `id`: Long (Primary Key)
- `click_date`: LocalDateTime
- `url_mapping_id`: Long (Foreign Key to UrlMappings)

## Dependencies

- `spring-boot-starter-web` - Web framework
- `spring-boot-starter-data-jpa` - Database ORM
- `lombok` - Annotation processor for reducing boilerplate
- `spring-boot-starter-test` - Testing framework

## Future Enhancements

- REST API endpoints for URL creation and retrieval
- URL expiration policies
- Custom short URL codes
- Advanced analytics dashboard
- URL categorization and tagging
- Batch URL shortening

## License

This project is part of a portfolio/resume projects collection.