package com.example.userservice.repository;

import com.example.userservice.entity.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    List<Watchlist> findByUserId(Long userId);
    void deleteByUserIdAndVideoId(Long userId, Long videoId);
}
