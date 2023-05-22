package edu.curso.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import edu.curso.domain.Ingrediente;
import edu.curso.domain.OrdenPizza;
import edu.curso.domain.Pizza;
import edu.curso.domain.TipoIngrediente;
import edu.curso.models.jdbc.IngredienteJdbcRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("ordenPizza") //Pone el atributo ordenPizza que se define en el modelo más abajo en la session
public class DiseniarPizzaController {
	
	private IngredienteJdbcRepository ingredienteRepo;
	
	
	public DiseniarPizzaController(IngredienteJdbcRepository ingredienteRepo) {
		super();
		this.ingredienteRepo = ingredienteRepo;
	}

	@ModelAttribute	
	public void agregarIngredientesAlModelo(Model model) {
		
		//Obtengo todos los ingredientes declarados
		List<Ingrediente>  ingredientes = ingredienteRepo.findAll();
		
		TipoIngrediente[] tipos = TipoIngrediente.values();
		for (TipoIngrediente tipoIngrediente : tipos) {
			model.addAttribute(tipoIngrediente.toString().toLowerCase(), filterByType(ingredientes, tipoIngrediente));
		}
	}
	
	@ModelAttribute(name = "ordenPizza")
	public OrdenPizza orden() {
		return new OrdenPizza();
	}
	
	@ModelAttribute(name = "pizza")
	public Pizza pizza() {
		return new Pizza();
	}
	
	//Este método devuelve el nombre de la vista que antenderá el request en /design
	@GetMapping
	public String mostrarFormulario() {
		return "design";
	}
	
	private Iterable<Ingrediente> filterByType(List<Ingrediente> ingredientes, TipoIngrediente tipo) {
		return ingredientes.stream().filter(x -> x.getTipo().equals(tipo)).collect(Collectors.toList());
		
	}
	
	
	/**
	 * Metodo encargado de procesar una nueva pizza
	 * Observar como se utiliza bean validation para realizar las validaciones
	 * @param pizza
	 * @param errores
	 * @param ordenPizza
	 * @return
	 */
	@PostMapping
	public String procesarOrden(@Valid Pizza pizza, Errors errores, @ModelAttribute OrdenPizza ordenPizza) {
		log.info("Procesando la pizza: {}", pizza);
		
		//Si encuentro errores, vuelvo a la página de diseño de la pizza
		if (errores.hasErrors()) {
			log.error("Se encontraror errores al validar: {}", errores.getAllErrors());
			return "design";
		}
		
		ordenPizza.addPizza(pizza);
		
		return "redirect:/ordenes/actual";
	}

}
