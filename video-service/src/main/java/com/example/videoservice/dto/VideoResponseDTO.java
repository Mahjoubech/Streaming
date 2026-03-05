package com.example.videoservice.dto;

import com.example.videoservice.entity.enums.VideoCategory;
import com.example.videoservice.entity.enums.VideoType;
import lombok.*;

/**
 * DTO returned by the API for video resources (output payload).
 * STREAM-34 — Create DTOs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private VideoType type;
    private VideoCategory category;
    private Double rating;
    private String director;
    private String cast;
}
