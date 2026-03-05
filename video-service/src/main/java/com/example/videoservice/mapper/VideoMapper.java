package com.example.videoservice.mapper;

import com.example.videoservice.dto.VideoRequestDTO;
import com.example.videoservice.dto.VideoResponseDTO;
import com.example.videoservice.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * MapStruct mapper between Video entity and DTOs.
 * STREAM-35 — Create Mapper
 */
@Mapper(componentModel = "spring")
public interface VideoMapper {

    /** Map entity → response DTO */
    VideoResponseDTO toResponseDTO(Video video);

    /** Map request DTO → entity (for creation) */
    Video toEntity(VideoRequestDTO dto);

    /** Merge request DTO fields into an existing entity (for update) */
    void updateEntityFromDTO(VideoRequestDTO dto, @MappingTarget Video video);

    /** Map a list of entities → list of response DTOs */
    List<VideoResponseDTO> toResponseDTOList(List<Video> videos);
}
