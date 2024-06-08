package dev.petermurphy.picturehaven.artist;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository {

    List<Artist> findAll();

    Optional<Artist> findById(Integer id);

    void create(Artist artist);

    void update(Artist artist, Integer id);

    void delete(Integer id);

    long count();
}
