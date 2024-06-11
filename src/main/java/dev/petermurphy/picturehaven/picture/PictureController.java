package dev.petermurphy.picturehaven.picture;

import dev.petermurphy.picturehaven.service.PictureService;
import dev.petermurphy.picturehaven.tag.Tag;
import dev.petermurphy.picturehaven.tag.TagRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pictures")
public class PictureController {

	private final PictureRepository pictureRepository;
	private final TagRepository tagRepository;

	public PictureController(PictureRepository pictureRepository, TagRepository tagRepository) {
		this.pictureRepository = pictureRepository;
		this.tagRepository = tagRepository;
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
        pictureRepository.create(picture);
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
	
	@GetMapping("/tags/{id}")
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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/{pictureId}/tags/{tagId}")
	void addTagToPicture(@PathVariable Integer pictureId, @PathVariable Integer tagId) {
		pictureRepository.addTagToPicture(pictureId, tagId);
	}

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
