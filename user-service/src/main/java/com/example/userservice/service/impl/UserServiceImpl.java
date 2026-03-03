package com.streaming.user.service.impl;

import com.streaming.user.client.VideoClient;
import com.streaming.user.dto.UserDTO;
import com.streaming.user.dto.WatchHistoryDTO;
import com.streaming.user.dto.WatchlistDTO;
import com.streaming.user.entity.User;
import com.streaming.user.entity.WatchHistory;
import com.streaming.user.entity.Watchlist;
import com.streaming.user.mapper.UserMapper;
import com.streaming.user.mapper.WatchHistoryMapper;
import com.streaming.user.mapper.WatchlistMapper;
import com.streaming.user.repository.UserRepository;
import com.streaming.user.repository.WatchHistoryRepository;
import com.streaming.user.repository.WatchlistRepository;
import com.streaming.user.service.UserService;
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










}
