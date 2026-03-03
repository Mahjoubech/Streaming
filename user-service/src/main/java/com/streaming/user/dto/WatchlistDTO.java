package com.streaming.user.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchlistDTO {
    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime addedAt;
    private VideoDTO video;
}
