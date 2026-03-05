package com.example.videoservice.repository;

import com.example.videoservice.entity.Video;
import com.example.videoservice.entity.enums.VideoCategory;
import com.example.videoservice.entity.enums.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Video entity.
 * STREAM-27 — Create JPA Repository
 */
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    List<Video> findByType(VideoType type);

    List<Video> findByCategory(VideoCategory category);

    List<Video> findByTitleContainingIgnoreCase(String title);

    List<Video> findByDirectorContainingIgnoreCase(String director);
}
