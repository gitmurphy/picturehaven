package dev.petermurphy.picturehaven.picture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcPictureRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcPictureRepositoryTest {

    // Inject the JdbcPictureRepository into the test
    @Autowired
    JdbcPictureRepository jdbcPictureRepository;
    long dbPictureCount;
    long dbPictureCountPerArtist;

    // Get current picture count and picture count per artist before each test
    @BeforeEach
    void setUp() {
        dbPictureCount = jdbcPictureRepository.count();
        dbPictureCountPerArtist = jdbcPictureRepository.countPerArtist(5);
    }

    @Test
    void shouldFindAllPictures() {
        List<Picture> pictures = jdbcPictureRepository.findAll();
        assertEquals(dbPictureCount, pictures.size());
    }

    @Test
    void shouldFindAllPicturesByArtist() {
        List<Picture> artistPictures = jdbcPictureRepository.findAllByArtist(5);
        assertEquals(dbPictureCountPerArtist, artistPictures.size());
    }

    @Test
    void shouldCreatePicture() {
        Picture picture = new Picture(null, "title", "description", "filepath", 10);
        jdbcPictureRepository.create(picture);
        List<Picture> pictures = jdbcPictureRepository.findAll();
        assertEquals(dbPictureCount + 1, pictures.size());
    }

    @Test
    void shouldUpdatePicture() {
        Picture picture = new Picture(null, "title", "description", "filepath", 10);
        // insert a new picture to test the update method
        jdbcPictureRepository.create(picture);
        List<Picture> pictures = jdbcPictureRepository.findAll();
        Picture pictureToUpdate = pictures.get(pictures.size() - 1);
        pictureToUpdate = new Picture(pictureToUpdate.id(), "titleUpdated", "descriptionUpdated", "filepathUpdated", 10);
        // update the picture
        jdbcPictureRepository.update(pictureToUpdate, pictureToUpdate.id());
        Picture updatedPicture = jdbcPictureRepository.findById(pictureToUpdate.id()).get();
        assertEquals(pictureToUpdate, updatedPicture);
    }

    @Test
    void shouldDeletePicture() {
        Picture picture = new Picture(null, "title", "description", "filepath", 10);
        // insert a new picture to test the delete method
        jdbcPictureRepository.create(picture);
        List<Picture> pictures = jdbcPictureRepository.findAll();
        Picture pictureToDelete = pictures.get(pictures.size() - 1);
        // delete the picture
        jdbcPictureRepository.delete(pictureToDelete.id());
        long countAfterDelete = jdbcPictureRepository.count();
        assertEquals(dbPictureCount, countAfterDelete);
    }

    @Test
    void shouldAddTagToPicture() {
        Picture picture = new Picture(null, "title", "description", "filepath", 10);
        // insert a new picture to add a tag to
        jdbcPictureRepository.create(picture);
        List<Picture> pictures = jdbcPictureRepository.findAll();
        Picture pictureToTag = pictures.get(pictures.size() - 1);
        // add a tag to the picture
        jdbcPictureRepository.addTagToPicture(pictureToTag.id(), 1);
        List<Integer> tags = jdbcPictureRepository.findTagsByPicture(pictureToTag.id());
        assertEquals(1, tags.size());
    }
}