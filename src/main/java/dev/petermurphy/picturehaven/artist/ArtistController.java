package dev.petermurphy.picturehaven.artist;

import dev.petermurphy.picturehaven.picture.Picture;
import dev.petermurphy.picturehaven.picture.PictureRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final PictureRepository pictureRepository;

    public ArtistController(ArtistRepository artistRepository, PictureRepository pictureRepository) {
        this.artistRepository = artistRepository;
        this.pictureRepository = pictureRepository;
    }

    @Tag(name = "get")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Tag(name = "get")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Artist> findById(@PathVariable Integer id) {
        return artistRepository.findById(id);
    }

    @Tag(name = "post")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Artist artist) {
        artistRepository.create(artist);
    }

    @Tag(name = "put")
    @PutMapping("/{id}")
    void update(@PathVariable Integer id, @Valid @RequestBody Artist artist) {
        artistRepository.update(artist, id);
    }

    @Tag(name = "delete")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        artistRepository.delete(id);
    }

    @Tag(name = "get")
    @GetMapping("/count")
    long count() {
        return artistRepository.count();
    }

    @Tag(name = "get")
    @GetMapping("/pictures/{id}")
    List<Picture> findPicturesByArtist(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        return pictures;
    }

    @Tag(name = "delete")
    @DeleteMapping("/pictures/{artistId}")
    void deletePicturesByArtist(@PathVariable Integer artistId) {
        List<Picture> pictures = pictureRepository.findAllByArtist(artistId);
        for (Picture picture : pictures) {
            pictureRepository.delete(picture.id());
        }
    }

    @Tag(name = "get")
    @GetMapping("/artistpictures/{id}")
    ArtistPictures getArtistWithPictures(@PathVariable Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        return new ArtistPictures(artist, pictures);
    }

    @Tag(name = "delete")
    @DeleteMapping("/artistpictures/{id}")
    void deleteArtistAndPictures(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        for (Picture picture : pictures) {
            pictureRepository.delete(picture.id());
        }
        artistRepository.delete(id);
    }
}