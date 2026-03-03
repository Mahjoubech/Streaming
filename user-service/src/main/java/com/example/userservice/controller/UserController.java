package com.streaming.user.controller;

import com.streaming.user.dto.UserDTO;
import com.streaming.user.dto.WatchHistoryDTO;
import com.streaming.user.dto.WatchlistDTO;
import com.streaming.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<Void> addToWatchlist(@PathVariable Long userId, @PathVariable Long videoId) {
        userService.addToWatchlist(userId, videoId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{userId}/watchlist/{videoId}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable Long userId, @PathVariable Long videoId) {
        userService.removeFromWatchlist(userId, videoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/watchlist")
    public ResponseEntity<List<WatchlistDTO>> getWatchlist(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getWatchlist(userId));
    }

    @PostMapping("/{userId}/history/{videoId}")
    public ResponseEntity<Void> recordHistory(
            @PathVariable Long userId, 
            @PathVariable Long videoId,
            @RequestParam Integer progressTime,
            @RequestParam Boolean completed) {
        userService.recordWatchHistory(userId, videoId, progressTime, completed);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<WatchHistoryDTO>> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getWatchHistory(userId));
    }
}
