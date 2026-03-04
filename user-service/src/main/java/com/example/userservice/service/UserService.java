package com.example.userservice.service;

import com.example.userservice.dto.UserDTO;
import com.example.userservice.dto.WatchHistoryDTO;
import com.example.userservice.dto.WatchlistDTO;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    void addToWatchlist(Long userId, Long videoId);
    void removeFromWatchlist(Long userId, Long videoId);
    List<WatchlistDTO> getWatchlist(Long userId);
    void recordWatchHistory(Long userId, Long videoId, Integer progressTime, Boolean completed);
    List<WatchHistoryDTO> getWatchHistory(Long userId);
    Map<String, Object> getWatchStatistics(Long userId);

}
