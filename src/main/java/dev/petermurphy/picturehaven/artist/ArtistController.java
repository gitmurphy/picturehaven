package dev.petermurphy.picturehaven.artist;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistRepository artistRepository;

    public ArtistController(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
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
}