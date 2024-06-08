package dev.petermurphy.picturehaven.tag;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;

public record Tag(
	@Id
	Integer id,
	@NotEmpty
	String name
){
	// record methods
}
