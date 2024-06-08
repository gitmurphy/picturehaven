package dev.petermurphy.picturehaven.tag;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
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
}
