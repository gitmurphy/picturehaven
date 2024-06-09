package dev.petermurphy.picturehaven.artist;

import dev.petermurphy.picturehaven.picture.Picture;

import java.util.List;
import java.util.Optional;

public record ArtistPictures(
    Optional<Artist> artist,
    List<Picture> pictures
) {
    // record methods
}
