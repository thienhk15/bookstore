package com.thien.app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String title;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String description;
    @Column(name = "release_year", length = 4)
    private String releaseYear;
    @Column(length = 255)
    private String language;
    @Column(name = "image_url", length = 255)
    private String image;
    @Column(nullable = false)
    private Long price;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "publisher_id")
    private Long publisherId;
    @Column(name = "overall_rating")
    private Long overallRating;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "update_at")
    private Date updateAt;

    public Book(String title, String description, String releaseYear, String language, String image, Long price, Long categoryId, Long authorId, Long publisherId, Long overallRating, Date createAt, Date updateAt) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = language;
        this.image = image;
        this.price = price;
        this.categoryId = categoryId;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.overallRating = overallRating;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public Book(String title, String description, String releaseYear, Long price) {
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.language = "vi";
        this.image = "https://salt.tikicdn.com/cache/750x750/ts/product/54/35/e1/ba5234c7edfa41531b337c77356a99d0.jpg.webp";
        this.price = price;
        this.categoryId = null;
        this.authorId = null;
        this.publisherId = null;
        this.overallRating = null;
        this.createAt = null;
        this.updateAt = null;
    }
}
