package com.example.videoservice.exception;

/**
 * Thrown when a video cannot be found by its ID.
 * STREAM-37 — Create controller et services
 */
public class VideoNotFoundException extends RuntimeException {

    public VideoNotFoundException(Long id) {
        super("Video not found with id: " + id);
    }
}
