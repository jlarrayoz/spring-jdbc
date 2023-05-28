package edu.curso.domain;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Pizza {
	
	private Long id;
	
	@NotNull
	@Size(min=5, max=20, message="El largo mínimo debe ser {min} y el máximo {max}")
	private String nombre;
	
	@NotNull
	@Size(min=2, message="La pizza debe tener al menos 2 ingredientes")
	private List<IngredientePizza> ingredientes;
}
