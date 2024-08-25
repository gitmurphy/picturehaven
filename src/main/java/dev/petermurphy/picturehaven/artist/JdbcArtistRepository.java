package dev.petermurphy.picturehaven.artist;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcArtistRepository implements ArtistRepository {

    private final JdbcClient jdbcClient;

    public JdbcArtistRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Artist> findAll() {
        return jdbcClient.sql("SELECT * FROM artists")
                .query(Artist.class)
                .list();
    }

    public Optional<Artist> findById(Integer id) {
        return jdbcClient.sql("SELECT id,name,bio,nationality FROM artists WHERE id = :id")
                .param("id", id)
                .query(Artist.class)
                .optional();
    }

    public void create(Artist artist) {
        System.out.println("Creating artist: " + artist);
        var updated = jdbcClient.sql("INSERT INTO artists(name, bio, nationality) VALUES(?, ?, ?)")
                .params(List.of(artist.name(), artist.bio(), artist.nationality()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + artist.name());
    }

    public void update(Artist artist, Integer id) {
        var updated = jdbcClient.sql("UPDATE artists SET name = ?, bio = ?, nationality = ? WHERE id = ?")
                .params(List.of(artist.name(), artist.bio(), artist.nationality(), id))
                .update();

        Assert.state(updated == 1, "Failed to update artists " + artist.name());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM artists where id = ?")
                .params(List.of(id))
                .update();

        Assert.state(updated == 1, "Failed to delete artist " + id);
    }

    public long count() {
        return jdbcClient.sql("SELECT * FROM artists").query().listOfRows().size();
    }
}
