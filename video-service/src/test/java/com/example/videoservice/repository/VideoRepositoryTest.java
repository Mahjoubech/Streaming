package com.example.videoservice.repository;

import com.example.videoservice.entity.Video;
import com.example.videoservice.entity.enums.VideoCategory;
import com.example.videoservice.entity.enums.VideoType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository slice tests using H2 in-memory database.
 * STREAM-40 — Test repository
 */
@DataJpaTest
class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;

    private Video inception;
    private Video interstellar;

    @BeforeEach
    void setUp() {
        videoRepository.deleteAll();

        inception = videoRepository.save(Video.builder()
                .title("Inception")
                .description("A thief who steals information from within dreams")
                .trailerUrl("https://www.youtube.com/embed/YoHD9XEInc0")
                .duration(148)
                .releaseYear(2010)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.8)
                .director("Christopher Nolan")
                .cast("Leonardo DiCaprio, Joseph Gordon-Levitt")
                .build());

        interstellar = videoRepository.save(Video.builder()
                .title("Interstellar")
                .description("A team of explorers travel through a wormhole")
                .trailerUrl("https://www.youtube.com/embed/zSWdZVtXT7E")
                .duration(169)
                .releaseYear(2014)
                .type(VideoType.FILM)
                .category(VideoCategory.SCIENCE_FICTION)
                .rating(8.6)
                .director("Christopher Nolan")
                .cast("Matthew McConaughey, Anne Hathaway")
                .build());
    }

    @Test
    @DisplayName("save — should persist a video and auto-generate ID")
    void save_success() {
        Video saved = videoRepository.save(Video.builder()
                .title("Tenet")
                .type(VideoType.FILM)
                .category(VideoCategory.ACTION)
                .director("Christopher Nolan")
                .build());

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("Tenet");
    }

    @Test
    @DisplayName("findById — should return video when it exists")
    void findById_found() {
        Optional<Video> found = videoRepository.findById(inception.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("findById — should return empty when ID does not exist")
    void findById_notFound() {
        Optional<Video> found = videoRepository.findById(9999L);
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("findAll — should return all persisted videos")
    void findAll_returnAll() {
        List<Video> all = videoRepository.findAll();
        assertThat(all).hasSize(2);
    }

    @Test
    @DisplayName("findByType — should return only FILM type videos")
    void findByType_film() {
        List<Video> films = videoRepository.findByType(VideoType.FILM);
        assertThat(films).hasSize(2).allMatch(v -> v.getType() == VideoType.FILM);
    }

    @Test
    @DisplayName("findByCategory — should return only SCIENCE_FICTION videos")
    void findByCategory_scienceFiction() {
        List<Video> sciFi = videoRepository.findByCategory(VideoCategory.SCIENCE_FICTION);
        assertThat(sciFi).hasSize(2);
    }

    @Test
    @DisplayName("findByTitleContainingIgnoreCase — should find by partial title (case-insensitive)")
    void findByTitle_caseInsensitive() {
        List<Video> results = videoRepository.findByTitleContainingIgnoreCase("inception");
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Inception");
    }

    @Test
    @DisplayName("findByDirectorContainingIgnoreCase — should find all Nolan films")
    void findByDirector() {
        List<Video> results = videoRepository.findByDirectorContainingIgnoreCase("Nolan");
        assertThat(results).hasSize(2);
    }

    @Test
    @DisplayName("deleteById — should remove the video from the database")
    void deleteById_success() {
        videoRepository.deleteById(inception.getId());
        assertThat(videoRepository.findById(inception.getId())).isEmpty();
        assertThat(videoRepository.findAll()).hasSize(1);
    }
}
