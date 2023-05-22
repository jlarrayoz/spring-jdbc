package edu.curso.models;

import edu.curso.domain.OrdenPizza;

public interface OrdenPizzaRepository {
	
	//Metodo encargado de guardar a la BD una orden de pizza (Con sus respectivas pizzas)
	OrdenPizza save(OrdenPizza orden);

}
