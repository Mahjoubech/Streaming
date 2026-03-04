package com.example.userservice.client;

import com.example.userservice.dto.VideoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "video-service")
public interface VideoClient {
    @GetMapping("/api/videos/{id}")
    VideoDTO getVideoById(@PathVariable("id") Long id);
}
