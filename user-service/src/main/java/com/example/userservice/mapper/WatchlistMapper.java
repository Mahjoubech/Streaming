package com.example.userservice.mapper;

import com.example.userservice.dto.WatchlistDTO;
import com.example.userservice.entity.Watchlist;
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
