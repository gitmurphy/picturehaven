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

	@Tag(name = "get", description = "Retrieves a list of all pictures.")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("")
	List<Picture> findAll() {
		return pictureRepository.findAll();
	}
	
	@Tag(name = "get", description = "Retrieves a picture by its ID.")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	Optional<Picture> findById(@PathVariable Integer id) {
		return pictureRepository.findById(id);
	}
	
	@Tag(name = "post", description = "Creates a new picture.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Picture picture) {
        pictureRepository.create(picture);
    }

	@Tag(name = "put", description = "Updates an existing picture.")
	@PutMapping("/{id}")
	void update(@PathVariable Integer id, @Valid @RequestBody Picture picture) {
		pictureRepository.update(picture, id);
	}

	@Tag(name = "delete", description = "Deletes a picture.")
	@DeleteMapping("/{id}")
	void delete(@PathVariable Integer id) {
		pictureRepository.delete(id);
	}

	@Tag(name = "get", description = "Returns the total number of pictures.")
	@GetMapping("/count")
	long count() {
		return pictureRepository.count();
	}

	@Tag(name = "post", description = "Add a tag to a picture.")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{pictureId}/tags/{tagId}")
	void addTagToPicture(@PathVariable Integer pictureId, @PathVariable Integer tagId) {
		pictureRepository.addTagToPicture(pictureId, tagId);
	}

	@Tag(name = "get", description = "Analyzes the image file associated with a picture and returns its dominant colors.")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/colors/{id}")
	Map<Color, Integer> getDominantColorsById(@PathVariable Integer id) throws IOException {
		BufferedImage image = null;
		String filePath = pictureRepository.findById(id).get().filepath();
		try {
			image = ImageIO.read(new File("src/main/java/dev/petermurphy/picturehaven/picture" + filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getDominantColors(image, 5);
	}

	Map<Color, Integer> getDominantColors(BufferedImage image, Integer maxCount) {
		return PictureService.getDominantColors(image, maxCount);
	}
}
