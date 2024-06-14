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

    // fully qualified names are required here because of ambiguity with the Tag class
    @io.swagger.v3.oas.annotations.tags.Tag(name = "get")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @io.swagger.v3.oas.annotations.tags.Tag(name = "get")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Tag> findById(@PathVariable Integer id) {
        return tagRepository.findById(id);
    }

    @io.swagger.v3.oas.annotations.tags.Tag(name = "post")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Tag tag) {
        tagRepository.create(tag);
    }

    @io.swagger.v3.oas.annotations.tags.Tag(name = "delete")
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        tagRepository.delete(id);
    }

    @io.swagger.v3.oas.annotations.tags.Tag(name = "get")
    @GetMapping("/count")
    long count() {
        return tagRepository.count();
    }

    @io.swagger.v3.oas.annotations.tags.Tag(name = "get")
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

    @io.swagger.v3.oas.annotations.tags.Tag(name = "get")
    @GetMapping("/picturetags/{id}")
    List<Tag> findTagsByPicture(@PathVariable Integer id) {
        List<Integer> tagIds = pictureRepository.findTagsByPicture(id);
        List<Tag> tags = new ArrayList<>();
        for (Integer tagId : tagIds) {
            Optional<Tag> optionalTag = tagRepository.findById(tagId);
            if (optionalTag.isPresent()) {
                tags.add(optionalTag.get());
            }
        }
        return tags;
    }
}
