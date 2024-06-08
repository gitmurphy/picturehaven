package dev.petermurphy.picturehaven.picture;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {

	private final PictureRepository pictureRepository;

	public PictureController(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("")
	List<Picture> findAll() {
		return pictureRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	Optional<Picture> findById(@PathVariable Integer id) {
		return pictureRepository.findById(id);
	}
	
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Picture picture) {
        pictureRepository.save(picture);
    }

	@PutMapping("/{id}")
	void update(@PathVariable Integer id, @Valid @RequestBody Picture picture) {
		pictureRepository.update(picture, id);
	}

	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		pictureRepository.delete(id);
	}

	@GetMapping("/count")
	long count() {
		return pictureRepository.count();
	}
}