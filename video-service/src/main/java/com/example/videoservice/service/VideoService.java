package com.example.videoservice.service;

import com.example.videoservice.dto.VideoRequestDTO;
import com.example.videoservice.dto.VideoResponseDTO;

import java.util.List;

/**
 * Contract for video business operations.
 * STREAM-37 — Create controller et services
 */
public interface VideoService {

    VideoResponseDTO createVideo(VideoRequestDTO requestDTO);

    VideoResponseDTO getVideoById(Long id);

    List<VideoResponseDTO> getAllVideos();

    VideoResponseDTO updateVideo(Long id, VideoRequestDTO requestDTO);

    void deleteVideo(Long id);
}
