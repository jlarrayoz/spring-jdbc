package edu.curso.domain;

import lombok.Data;


@Data
public class Ingrediente {
	private final String id;
	private final String nombre;
	private final TipoIngrediente tipo;
}
