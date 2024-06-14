package dev.petermurphy.picturehaven.picture;

import dev.petermurphy.picturehaven.service.PictureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {

	private final PictureRepository pictureRepository;

	public PictureController(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}

	@Tag(name = "get", description = "Get method endpoints for REST API.")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("")
	List<Picture> findAll() {
		return pictureRepository.findAll();
	}
	
	@Tag(name = "get")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	Optional<Picture> findById(@PathVariable Integer id) {
		return pictureRepository.findById(id);
	}
	
	@Tag(name = "post", description = "Post method endpoints for REST API.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Picture picture) {
        pictureRepository.create(picture);
    }

	@Tag(name = "put", description = "Put method endpoints for REST API.")
	@PutMapping("/{id}")
	void update(@PathVariable Integer id, @Valid @RequestBody Picture picture) {
		pictureRepository.update(picture, id);
	}

	@Tag(name = "delete", description = "Delete method endpoints for REST API.")
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		pictureRepository.delete(id);
	}

	@Tag(name = "get")
	@GetMapping("/count")
	long count() {
		return pictureRepository.count();
	}

	@Tag(name = "post")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{pictureId}/tags/{tagId}")
	void addTagToPicture(@PathVariable Integer pictureId, @PathVariable Integer tagId) {
		pictureRepository.addTagToPicture(pictureId, tagId);
	}

	@Tag(name = "get")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/colors/{id}/{maxCount}")
	Map<Color, Integer> getDominantColorsById(@PathVariable Integer id, @PathVariable Integer maxCount) throws IOException {
		BufferedImage image = null;
		String filePath = pictureRepository.findById(id).get().filepath();
		try {
			image = ImageIO.read(new File("src/main/java/dev/petermurphy/picturehaven/picture" + filePath));
		} catch (IOException e) {
			throw new IOException("Filepath not found for picture with id " + id, e);
		}
		return getDominantColors(image, maxCount);
	}

	Map<Color, Integer> getDominantColors(BufferedImage image, Integer maxCount) {
		return PictureService.getDominantColors(image, maxCount);
	}
}
