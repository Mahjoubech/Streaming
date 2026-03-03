package com.streaming.video.dto;

import com.streaming.video.entity.Video;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoDTO {
    private Long id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private String type;
    private String category;
    private Double rating;
    private String director;
    private String cast;
}
