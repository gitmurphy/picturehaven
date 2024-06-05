package dev.petermurphy.picturehaven.picturehaven

import org.springframework.data.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

//import java.time.LocalDateTime;

/* The Java compiler automatically generates essential methods for records, 
	including constructors, getters, equals(), hashCode(), and toString()*/
public record Tag(
	@Id
	Integer id,
	@NotEmpty
	String name
	// LocalDateTime upddate
	// LocalDateTime insdate
){
	// record methods
}