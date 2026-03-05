package com.example.videoservice.service.impl;

import com.example.videoservice.dto.VideoRequestDTO;
import com.example.videoservice.dto.VideoResponseDTO;
import com.example.videoservice.entity.Video;
import com.example.videoservice.entity.enums.VideoCategory;
import com.example.videoservice.entity.enums.VideoType;
import com.example.videoservice.exception.VideoNotFoundException;
import com.example.videoservice.mapper.VideoMapper;
import com.example.videoservice.repository.VideoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for VideoServiceImpl.
 * STREAM-39 — Test service layer
 */
@ExtendWith(MockitoExtension.class)
class VideoServiceImplTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoServiceImpl videoService;

    private Video video;
    private VideoRequestDTO requestDTO;
    private VideoResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        video = Video.builder()
                .id(1L)
                .title("Inception")
                .description("A mind-bending thriller")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .build();

        requestDTO = VideoRequestDTO.builder()
                .title("Inception")
                .description("A mind-bending thriller")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .build();

        responseDTO = VideoResponseDTO.builder()
                .id(1L)
                .title("Inception")
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .build();
    }

    // ───── createVideo ─────────────────────────────────────────────────

    @Test
    @DisplayName("createVideo — should save and return VideoResponseDTO")
    void createVideo_success() {
        when(videoMapper.toEntity(requestDTO)).thenReturn(video);
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        VideoResponseDTO result = videoService.createVideo(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Inception");
        verify(videoRepository).save(video);
    }

    // ───── getVideoById ────────────────────────────────────────────────

    @Test
    @DisplayName("getVideoById — should return VideoResponseDTO when found")
    void getVideoById_found() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        VideoResponseDTO result = videoService.getVideoById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("getVideoById — should throw VideoNotFoundException when not found")
    void getVideoById_notFound() {
        when(videoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoService.getVideoById(99L))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ───── getAllVideos ─────────────────────────────────────────────────

    @Test
    @DisplayName("getAllVideos — should return list of VideoResponseDTOs")
    void getAllVideos_success() {
        when(videoRepository.findAll()).thenReturn(List.of(video));
        when(videoMapper.toResponseDTOList(List.of(video))).thenReturn(List.of(responseDTO));

        List<VideoResponseDTO> result = videoService.getAllVideos();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Inception");
    }

    // ───── updateVideo ─────────────────────────────────────────────────

    @Test
    @DisplayName("updateVideo — should update and return updated VideoResponseDTO")
    void updateVideo_success() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        doNothing().when(videoMapper).updateEntityFromDTO(requestDTO, video);
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toResponseDTO(video)).thenReturn(responseDTO);

        VideoResponseDTO result = videoService.updateVideo(1L, requestDTO);

        assertThat(result).isNotNull();
        verify(videoMapper).updateEntityFromDTO(requestDTO, video);
        verify(videoRepository).save(video);
    }

    @Test
    @DisplayName("updateVideo — should throw VideoNotFoundException when not found")
    void updateVideo_notFound() {
        when(videoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoService.updateVideo(99L, requestDTO))
                .isInstanceOf(VideoNotFoundException.class);
    }

    // ───── deleteVideo ─────────────────────────────────────────────────

    @Test
    @DisplayName("deleteVideo — should delete when video exists")
    void deleteVideo_success() {
        when(videoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(videoRepository).deleteById(1L);

        assertThatCode(() -> videoService.deleteVideo(1L)).doesNotThrowAnyException();
        verify(videoRepository).deleteById(1L);
    }

    @Test
    @DisplayName("deleteVideo — should throw VideoNotFoundException when not found")
    void deleteVideo_notFound() {
        when(videoRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> videoService.deleteVideo(99L))
                .isInstanceOf(VideoNotFoundException.class);
    }
}
