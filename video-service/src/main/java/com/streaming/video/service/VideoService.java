package com.streaming.video.service;

import com.streaming.video.dto.VideoDTO;
import java.util.List;

public interface VideoService {
    VideoDTO createVideo(VideoDTO videoDTO);
    VideoDTO updateVideo(Long id, VideoDTO videoDTO);
    void deleteVideo(Long id);
    VideoDTO getVideoById(Long id);
    List<VideoDTO> getAllVideos();
    List<VideoDTO> searchVideosByTitle(String title);
    List<VideoDTO> getVideosByCategory(String category);
}
