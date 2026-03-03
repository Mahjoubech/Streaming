package com.streaming.video.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    
    @Enumerated(EnumType.STRING)
    private VideoType type;
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    private Double rating;
    private String director;
    private String cast;

    public enum VideoType {
        FILM, SERIE
    }

    public enum Category {
        ACTION, COMEDIE, DRAME, SCIENCE_FICTION, THRILLER, HORREUR
    }
}
