package com.example.videoservice.controller;

import com.example.videoservice.dto.VideoRequestDTO;
import com.example.videoservice.dto.VideoResponseDTO;
import com.example.videoservice.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for video management.
 * STREAM-37 — Create controller et services
 *
 * Base URL: /api/videos
 */
@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    /**
     * POST /api/videos
     * Create a new video.
     */
    @PostMapping
    public ResponseEntity<VideoResponseDTO> createVideo(@RequestBody @Valid VideoRequestDTO requestDTO) {
        VideoResponseDTO created = videoService.createVideo(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/videos
     * Retrieve all videos.
     */
    @GetMapping
    public ResponseEntity<List<VideoResponseDTO>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    /**
     * GET /api/videos/{id}
     * Retrieve a single video by its ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    /**
     * PUT /api/videos/{id}
     * Update an existing video.
     */
    @PutMapping("/{id}")
    public ResponseEntity<VideoResponseDTO> updateVideo(
            @PathVariable Long id,
            @RequestBody @Valid VideoRequestDTO requestDTO) {
        return ResponseEntity.ok(videoService.updateVideo(id, requestDTO));
    }

    /**
     * DELETE /api/videos/{id}
     * Delete a video by its ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }
}
