package dev.petermurphy.picturehaven.artist;

import dev.petermurphy.picturehaven.picture.Picture;
import dev.petermurphy.picturehaven.picture.PictureRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final PictureRepository pictureRepository;

    public ArtistController(ArtistRepository artistRepository, PictureRepository pictureRepository) {
        this.artistRepository = artistRepository;
        this.pictureRepository = pictureRepository;
    }

    @Tag(name = "get", description = "Retrieves a list of all artists.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Tag(name = "get", description = "Retrieves an artist by their ID.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Artist> findById(@PathVariable Integer id) {
        return artistRepository.findById(id);
    }

    @Tag(name = "post", description = "Creates a new artist.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Artist artist) {
        artistRepository.create(artist);
    }

    @Tag(name = "put", description = "Updates an existing artist.")
    @PutMapping("/{id}")
    void update(@PathVariable Integer id, @Valid @RequestBody Artist artist) {
        artistRepository.update(artist, id);
    }

    @Tag(name = "delete", description = "Deletes an artist.")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        artistRepository.delete(id);
    }

    @Tag(name = "get", description = "Returns the total number of artists.")
    @GetMapping("/count")
    long count() {
        return artistRepository.count();
    }

    @Tag(name = "get", description = "Get pictures associated with a specific artist")
    @GetMapping("/pictures/{id}")
    List<Picture> findPicturesByArtist(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        return pictures;
    }

    @Tag(name = "delete", description = "Deletes all pictures by artist.")
    @DeleteMapping("/pictures/{artistId}")
    void deletePicturesByArtist(@PathVariable Integer artistId) {
        List<Picture> pictures = pictureRepository.findAllByArtist(artistId);
        for (Picture picture : pictures) {
            pictureRepository.delete(picture.id());
        }
    }

    @Tag(name = "get", description = "Retrieves an artist with their pictures.")
    @GetMapping("/artistpictures/{id}")
    ArtistPictures getArtistWithPictures(@PathVariable Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        return new ArtistPictures(artist, pictures);
    }

    @Tag(name = "delete", description = "Deletes an artist with their pictures.")
    @DeleteMapping("/artistpictures/{id}")
    void deleteArtistAndPictures(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        for (Picture picture : pictures) {
            pictureRepository.delete(picture.id());
        }
        artistRepository.delete(id);
    }
}