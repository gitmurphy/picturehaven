package dev.petermurphy.picturehaven.tag;

import dev.petermurphy.picturehaven.picture.Picture;
import dev.petermurphy.picturehaven.picture.PictureRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepository;
    private final PictureRepository pictureRepository;

    public TagController(TagRepository tagRepository, PictureRepository pictureRepository) {
        this.tagRepository = tagRepository;
        this.pictureRepository = pictureRepository;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Tag> findById(@PathVariable Integer id) {
        return tagRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Tag tag) {
        tagRepository.create(tag);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        tagRepository.delete(id);
    }

    @GetMapping("/count")
    long count() {
        return tagRepository.count();
    }

    @GetMapping("/pictures/{id}")
    List<Picture> findPicturesByTag(@PathVariable Integer id) {
        List<Integer> pictureIds = tagRepository.findPicturesByTag(id);
        List<Picture> pictures = new ArrayList<>();
        for (Integer pictureId : pictureIds) {
            Optional<Picture> optionalPicture = pictureRepository.findById(pictureId);
            if (optionalPicture.isPresent()) {
                pictures.add(optionalPicture.get());
            }
        }
        return pictures;
    }
}
