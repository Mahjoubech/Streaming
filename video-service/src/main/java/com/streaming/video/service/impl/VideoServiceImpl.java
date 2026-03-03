package com.streaming.video.service.impl;

import com.streaming.video.dto.VideoDTO;
import com.streaming.video.entity.Video;
import com.streaming.video.mapper.VideoMapper;
import com.streaming.video.repository.VideoRepository;
import com.streaming.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    public VideoDTO createVideo(VideoDTO videoDTO) {
        Video video = videoMapper.toEntity(videoDTO);
        return videoMapper.toDTO(videoRepository.save(video));
    }

    @Override
    public VideoDTO updateVideo(Long id, VideoDTO videoDTO) {
        Video existingVideo = videoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
        
        existingVideo.setTitle(videoDTO.getTitle());
        existingVideo.setDescription(videoDTO.getDescription());
        existingVideo.setThumbnailUrl(videoDTO.getThumbnailUrl());
        existingVideo.setTrailerUrl(videoDTO.getTrailerUrl());
        existingVideo.setDuration(videoDTO.getDuration());
        existingVideo.setReleaseYear(videoDTO.getReleaseYear());
        existingVideo.setType(Video.VideoType.valueOf(videoDTO.getType()));
        existingVideo.setCategory(Video.Category.valueOf(videoDTO.getCategory()));
        existingVideo.setRating(videoDTO.getRating());
        existingVideo.setDirector(videoDTO.getDirector());
        existingVideo.setCast(videoDTO.getCast());
        
        return videoMapper.toDTO(videoRepository.save(existingVideo));
    }

    @Override
    public void deleteVideo(Long id) {
        videoRepository.deleteById(id);
    }

    @Override
    public VideoDTO getVideoById(Long id) {
        return videoRepository.findById(id)
                .map(videoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Video not found with id: " + id));
    }

    @Override
    public List<VideoDTO> getAllVideos() {
        return videoMapper.toDTOList(videoRepository.findAll());
    }

    @Override
    public List<VideoDTO> searchVideosByTitle(String title) {
        return videoMapper.toDTOList(videoRepository.findByTitleContainingIgnoreCase(title));
    }

    @Override
    public List<VideoDTO> getVideosByCategory(String category) {
        return videoMapper.toDTOList(videoRepository.findByCategory(Video.Category.valueOf(category)));
    }
}
