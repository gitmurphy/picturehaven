package dev.petermurphy.picturehaven.artist;

import dev.petermurphy.picturehaven.picture.Picture;
import dev.petermurphy.picturehaven.picture.PictureRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Artist> findById(@PathVariable Integer id) {
        return artistRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Artist artist) {
        artistRepository.create(artist);
    }

    @PutMapping("/{id}")
    void update(@PathVariable Integer id, @Valid @RequestBody Artist artist) {
        artistRepository.update(artist, id);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        artistRepository.delete(id);
    }

    @GetMapping("/count")
    long count() {
        return artistRepository.count();
    }

    @GetMapping("/pictures/{id}")
    List<Picture> findPicturesByArtist(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        return pictures;
    }

    @DeleteMapping("/pictures/{id}")
    void deletePicturesByArtist(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        for (Picture picture : pictures) {
            pictureRepository.delete(picture.id());
        }
    }

    @GetMapping("/artistpictures/{id}")
    ArtistPictures getArtistWithPictures(@PathVariable Integer id) {
        Optional<Artist> artist = artistRepository.findById(id);
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        return new ArtistPictures(artist, pictures);
    }

    @DeleteMapping("/artistpictures/{id}")
    void deleteArtistAndPictures(@PathVariable Integer id) {
        List<Picture> pictures = pictureRepository.findAllByArtist(id);
        for (Picture picture : pictures) {
            pictureRepository.delete(picture.id());
        }
        artistRepository.delete(id);
    }
}