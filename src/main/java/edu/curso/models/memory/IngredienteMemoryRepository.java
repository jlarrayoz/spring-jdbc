package edu.curso.models.memory;


import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import edu.curso.domain.Ingrediente;
import edu.curso.domain.TipoIngrediente;
import edu.curso.models.IngredienteRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IngredienteMemoryRepository implements IngredienteRepository {

	private static Map<String, Ingrediente> ingredientes;
	
	static {
		
		ingredientes = new HashMap<>();
		
		ingredientes.put("MC", new Ingrediente("MC", "Masa Común", TipoIngrediente.MASA));
		ingredientes.put("MM", new Ingrediente("MM", "Masa Madre", TipoIngrediente.MASA));
		ingredientes.put("MIT",new Ingrediente("MIT", "Masa italiana", TipoIngrediente.MASA));
		ingredientes.put("QM", new Ingrediente("QM", "Queso muzzarella", TipoIngrediente.QUESO));
		ingredientes.put("QD", new Ingrediente("QD", "Queso dambo", TipoIngrediente.QUESO));
	}

	@Getter(lazy = true)
	private static final IngredienteMemoryRepository instance = new IngredienteMemoryRepository();


	@Override
	public List<Ingrediente> findAll() {
		return new ArrayList<Ingrediente>(ingredientes.values());
	}

	@Override
	public Optional<Ingrediente> findById(String id) {
		return Optional.ofNullable(ingredientes.get(id));
	}

	@Override
	public Ingrediente save(Ingrediente ingrediente) {
		return ingredientes.put(ingrediente.getId(), ingrediente);
	}
}
