package com.example.userservice.service.impl;

import com.example.userservice.client.VideoClient;
import com.example.userservice.dto.UserDTO;
import com.example.userservice.dto.WatchHistoryDTO;
import com.example.userservice.dto.WatchlistDTO;
import com.example.userservice.entity.User;
import com.example.userservice.entity.WatchHistory;
import com.example.userservice.entity.Watchlist;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.mapper.WatchHistoryMapper;
import com.example.userservice.mapper.WatchlistMapper;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.WatchHistoryRepository;
import com.example.userservice.repository.WatchlistRepository;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WatchlistRepository watchlistRepository;
    private final WatchHistoryRepository watchHistoryRepository;
    private final UserMapper userMapper;
    private final WatchlistMapper watchlistMapper;
    private final WatchHistoryMapper watchHistoryMapper;
    private final VideoClient videoClient;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userMapper.toDTOList(userRepository.findAll());
    }
    @Override
    @Transactional
    public void addToWatchlist(Long userId, Long videoId) {
        Watchlist watchlist = Watchlist.builder()
                .userId(userId)
                .videoId(videoId)
                .addedAt(LocalDateTime.now())
                .build();
        watchlistRepository.save(watchlist);
    }
    @Override
    @Transactional
    public void removeFromWatchlist(Long userId, Long videoId) {
        watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
    }
    @Override
    public List<WatchlistDTO> getWatchlist(Long userId) {
        return watchlistRepository.findByUserId(userId).stream()
                .map(w -> {
                    WatchlistDTO dto = watchlistMapper.toDTO(w);
                    try {
                        dto.setVideo(videoClient.getVideoById(w.getVideoId()));
                    } catch (Exception e) {
                        // Video service might be down or video not found
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @Override
    @Transactional
    public void recordWatchHistory(Long userId, Long videoId, Integer progressTime, Boolean completed) {
        WatchHistory history = WatchHistory.builder()
                .userId(userId)
                .videoId(videoId)
                .progressTime(progressTime)
                .completed(completed)
                .watchedAt(LocalDateTime.now())
                .build();
        watchHistoryRepository.save(history);
    }
    @Override
    public List<WatchHistoryDTO> getWatchHistory(Long userId) {
        return watchHistoryRepository.findByUserId(userId).stream()
                .map(h -> {
                    WatchHistoryDTO dto = watchHistoryMapper.toDTO(h);
                    try {
                        dto.setVideo(videoClient.getVideoById(h.getVideoId()));
                    } catch (Exception e) {
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getWatchStatistics(Long userId) {
        List<WatchHistory> history = watchHistoryRepository.findByUserId(userId);
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVideosWatched", history.size());
        stats.put("completedVideos", history.stream().filter(WatchHistory::getCompleted).count());
        stats.put("totalWatchTime", history.stream().mapToInt(WatchHistory::getProgressTime).sum());
        return stats;
    }
}
