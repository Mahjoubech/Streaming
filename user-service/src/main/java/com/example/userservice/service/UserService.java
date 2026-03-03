package com.streaming.user.service;

import com.streaming.user.dto.UserDTO;
import com.streaming.user.dto.WatchHistoryDTO;
import com.streaming.user.dto.WatchlistDTO;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    void addToWatchlist(Long userId, Long videoId);
    void removeFromWatchlist(Long userId, Long videoId);
}
