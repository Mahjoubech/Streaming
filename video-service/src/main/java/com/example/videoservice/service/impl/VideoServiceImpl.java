package com.example.videoservice.service.impl;

import com.example.videoservice.dto.VideoRequestDTO;
import com.example.videoservice.dto.VideoResponseDTO;
import com.example.videoservice.entity.Video;
import com.example.videoservice.exception.VideoNotFoundException;
import com.example.videoservice.mapper.VideoMapper;
import com.example.videoservice.repository.VideoRepository;
import com.example.videoservice.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of VideoService with full CRUD logic.
 * STREAM-37 — Create controller et services
 */
@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    @Transactional
    public VideoResponseDTO createVideo(VideoRequestDTO requestDTO) {
        Video video = videoMapper.toEntity(requestDTO);
        Video saved = videoRepository.save(video);
        return videoMapper.toResponseDTO(saved);
    }

    @Override
    public VideoResponseDTO getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
        return videoMapper.toResponseDTO(video);
    }

    @Override
    public List<VideoResponseDTO> getAllVideos() {
        return videoMapper.toResponseDTOList(videoRepository.findAll());
    }

    @Override
    @Transactional
    public VideoResponseDTO updateVideo(Long id, VideoRequestDTO requestDTO) {
        Video existing = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
        videoMapper.updateEntityFromDTO(requestDTO, existing);
        Video updated = videoRepository.save(existing);
        return videoMapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException(id);
        }
        videoRepository.deleteById(id);
    }
}
