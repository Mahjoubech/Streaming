package com.streaming.user.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchHistoryDTO {
    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime watchedAt;
    private Integer progressTime;
    private Boolean completed;
    private VideoDTO video;
}
