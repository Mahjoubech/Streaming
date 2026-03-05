package com.example.videoservice.controller;

import com.example.videoservice.dto.VideoRequestDTO;
import com.example.videoservice.dto.VideoResponseDTO;
import com.example.videoservice.entity.enums.VideoCategory;
import com.example.videoservice.entity.enums.VideoType;
import com.example.videoservice.exception.GlobalExceptionHandler;
import com.example.videoservice.exception.VideoNotFoundException;
import com.example.videoservice.service.VideoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * MockMvc tests for VideoController.
 * STREAM-41 — MockMvc test controller
 */
@WebMvcTest(VideoController.class)
@Import(GlobalExceptionHandler.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoService videoService;

    @Autowired
    private ObjectMapper objectMapper;

    private VideoResponseDTO responseDTO;
    private VideoRequestDTO validRequest;

    @BeforeEach
    void setUp() {
        responseDTO = VideoResponseDTO.builder()
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

        validRequest = VideoRequestDTO.builder()
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
    }

    // ───── POST /api/videos ────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/videos — should return 201 and created video")
    void createVideo_returns201() throws Exception {
        when(videoService.createVideo(any(VideoRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.type").value("FILM"))
                .andExpect(jsonPath("$.category").value("SCIENCE_FICTION"));
    }

    @Test
    @DisplayName("POST /api/videos — should return 400 when title is blank")
    void createVideo_invalidTitle_returns400() throws Exception {
        VideoRequestDTO invalid = VideoRequestDTO.builder()
                .title("")                     // blank → @NotBlank violation
                .type(VideoType.FILM)
                .category(VideoCategory.ACTION)
                .build();

        mockMvc.perform(post("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.title").isNotEmpty());
    }

    @Test
    @DisplayName("POST /api/videos — should return 400 when trailerUrl is invalid")
    void createVideo_invalidTrailerUrl_returns400() throws Exception {
        VideoRequestDTO invalid = VideoRequestDTO.builder()
                .title("Test")
                .trailerUrl("https://www.vimeo.com/12345678901") // not YouTube embed
                .type(VideoType.FILM)
                .category(VideoCategory.ACTION)
                .build();

        mockMvc.perform(post("/api/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.trailerUrl").isNotEmpty());
    }

    // ───── GET /api/videos ─────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/videos — should return 200 and list of videos")
    void getAllVideos_returns200() throws Exception {
        when(videoService.getAllVideos()).thenReturn(List.of(responseDTO));

        mockMvc.perform(get("/api/videos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Inception"));
    }

    // ───── GET /api/videos/{id} ────────────────────────────────────────

    @Test
    @DisplayName("GET /api/videos/{id} — should return 200 when found")
    void getVideoById_found_returns200() throws Exception {
        when(videoService.getVideoById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/videos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Inception"));
    }

    @Test
    @DisplayName("GET /api/videos/{id} — should return 404 when not found")
    void getVideoById_notFound_returns404() throws Exception {
        when(videoService.getVideoById(99L)).thenThrow(new VideoNotFoundException(99L));

        mockMvc.perform(get("/api/videos/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("99")));
    }

    // ───── PUT /api/videos/{id} ────────────────────────────────────────

    @Test
    @DisplayName("PUT /api/videos/{id} — should return 200 with updated video")
    void updateVideo_returns200() throws Exception {
        VideoResponseDTO updated = VideoResponseDTO.builder()
                .id(1L).title("Inception Updated").type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION).build();

        when(videoService.updateVideo(eq(1L), any(VideoRequestDTO.class))).thenReturn(updated);

        mockMvc.perform(put("/api/videos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Inception Updated"));
    }

    @Test
    @DisplayName("PUT /api/videos/{id} — should return 404 when video does not exist")
    void updateVideo_notFound_returns404() throws Exception {
        when(videoService.updateVideo(eq(99L), any())).thenThrow(new VideoNotFoundException(99L));

        mockMvc.perform(put("/api/videos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound());
    }

    // ───── DELETE /api/videos/{id} ─────────────────────────────────────

    @Test
    @DisplayName("DELETE /api/videos/{id} — should return 204 when deleted")
    void deleteVideo_returns204() throws Exception {
        doNothing().when(videoService).deleteVideo(1L);

        mockMvc.perform(delete("/api/videos/1"))
                .andExpect(status().isNoContent());

        verify(videoService).deleteVideo(1L);
    }

    @Test
    @DisplayName("DELETE /api/videos/{id} — should return 404 when not found")
    void deleteVideo_notFound_returns404() throws Exception {
        doThrow(new VideoNotFoundException(99L)).when(videoService).deleteVideo(99L);

        mockMvc.perform(delete("/api/videos/99"))
                .andExpect(status().isNotFound());
    }
}
