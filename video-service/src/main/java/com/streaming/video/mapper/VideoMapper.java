package com.streaming.video.mapper;

import com.streaming.video.dto.VideoDTO;
import com.streaming.video.entity.Video;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VideoMapper {
    VideoDTO toDTO(Video video);
    Video toEntity(VideoDTO videoDTO);
    List<VideoDTO> toDTOList(List<Video> videos);
}
