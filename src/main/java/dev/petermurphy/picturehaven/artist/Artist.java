package dev.petermurphy.picturehaven.artist;

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;

public record Artist(
	@Id
	Integer id,
	@NotEmpty
	String name,
	String bio,
	String nationality
){
	// record methods
}