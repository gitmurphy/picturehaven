package dev.petermurphy.picturehaven.picture;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
	
public record Picture(
	@Id
	Integer id,
	@NotEmpty
	String title,
	String description,
	@NotEmpty
	String filepath
){
	// record methods
}
