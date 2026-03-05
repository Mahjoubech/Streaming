package com.example.videoservice.dto;

import com.example.videoservice.entity.enums.VideoCategory;
import com.example.videoservice.entity.enums.VideoType;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO used for creating/updating a video (input payload).
 * STREAM-34 — Create DTOs
 * STREAM-36 — Validate trailerUrl format
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoRequestDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private String thumbnailUrl;

    /**
     * Must be a valid YouTube embed URL.
     * Accepted patterns:
     *   https://www.youtube.com/embed/{videoId}
     *   https://youtube.com/embed/{videoId}
     */
    @Pattern(
        regexp = "^https://(?:www\\.)?youtube\\.com/embed/[\\w\\-]{11}.*$",
        message = "trailerUrl must be a valid YouTube embed URL (e.g. https://www.youtube.com/embed/<videoId>)"
    )
    private String trailerUrl;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    @Min(value = 1888, message = "Release year must be >= 1888")
    @Max(value = 2100, message = "Release year must be <= 2100")
    private Integer releaseYear;

    @NotNull(message = "Type is required (FILM or SERIE)")
    private VideoType type;

    @NotNull(message = "Category is required")
    private VideoCategory category;

    @DecimalMin(value = "0.0", message = "Rating must be >= 0.0")
    @DecimalMax(value = "10.0", message = "Rating must be <= 10.0")
    private Double rating;

    private String director;

    private String cast;
}
