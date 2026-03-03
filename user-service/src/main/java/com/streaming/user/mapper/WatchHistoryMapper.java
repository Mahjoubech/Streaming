package com.streaming.user.mapper;

import com.streaming.user.dto.WatchHistoryDTO;
import com.streaming.user.entity.WatchHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchHistoryMapper {
    @Mapping(target = "video", ignore = true)
    WatchHistoryDTO toDTO(WatchHistory watchHistory);
    
    WatchHistory toEntity(WatchHistoryDTO watchHistoryDTO);
    List<WatchHistoryDTO> toDTOList(List<WatchHistory> watchHistories);
}
