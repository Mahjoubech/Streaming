package com.streaming.user.mapper;

import com.streaming.user.dto.WatchlistDTO;
import com.streaming.user.entity.Watchlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WatchlistMapper {
    @Mapping(target = "video", ignore = true)
    WatchlistDTO toDTO(Watchlist watchlist);
    
    Watchlist toEntity(WatchlistDTO watchlistDTO);
    List<WatchlistDTO> toDTOList(List<Watchlist> watchlists);
}
