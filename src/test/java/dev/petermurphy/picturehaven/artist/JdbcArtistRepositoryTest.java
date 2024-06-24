package dev.petermurphy.picturehaven.artist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@Import(JdbcArtistRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JdbcArtistRepositoryTest {

    @Autowired
    JdbcArtistRepository jdbcArtistRepository;
    long dbArtistCount;

    // Get current artist count before each test
    @BeforeEach
    void setUp() {
        dbArtistCount = jdbcArtistRepository.count();
    }

    @Test
    void shouldFindAllArtists() {
        List<Artist> artists = jdbcArtistRepository.findAll();
        assertEquals(dbArtistCount, artists.size());
    }

    @Test
    void shouldCreateArtist() {
        Artist artist = new Artist(null, "John Tester", "An artist tester", "Testland");
        jdbcArtistRepository.create(artist);
        List<Artist> artists = jdbcArtistRepository.findAll();
        assertEquals(dbArtistCount + 1, artists.size());
    }

    @Test
    void shouldUpdateArtist() {
        Artist artist = new Artist(null, "John Tester", "An artist tester", "Testland");
        // insert a new artist to test the update method
        jdbcArtistRepository.create(artist);
        List<Artist> artists = jdbcArtistRepository.findAll();
        Artist artistToUpdate = artists.get(artists.size() - 1);
        artistToUpdate = new Artist(artistToUpdate.id(), "John Tester Updated", "An artist tester updated", "Testland");
        // update the artist
        jdbcArtistRepository.update(artistToUpdate, artistToUpdate.id());
        Artist updatedArtist = jdbcArtistRepository.findById(artistToUpdate.id()).get();
        assertEquals(artistToUpdate, updatedArtist);
    }

    @Test
    void shouldDeleteArtist() {
        Artist artist = new Artist(null, "John Tester", "An artist tester", "Testland");
        // insert a new artist to test the delete method
        jdbcArtistRepository.create(artist);
        List<Artist> artists = jdbcArtistRepository.findAll();
        Artist artistToDelete = artists.get(artists.size() - 1);
        // delete the artist
        jdbcArtistRepository.delete(artistToDelete.id());
        long countAfterDelete = jdbcArtistRepository.count();
        assertEquals(dbArtistCount, countAfterDelete);
    }
}
