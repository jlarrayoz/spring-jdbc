package edu.curso.models.jdbc;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.asm.Type;

import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import edu.curso.domain.IngredientePizza;
import edu.curso.domain.OrdenPizza;
import edu.curso.domain.Pizza;
import edu.curso.models.OrdenPizzaRepository;

@Repository
public class OrdenPizzaJdbcRepository implements OrdenPizzaRepository {

	
	private JdbcOperations jdbcOperations;
	
	
	
	public OrdenPizzaJdbcRepository(JdbcOperations jdbcOperations) {
		super();
		this.jdbcOperations = jdbcOperations;
	}



	@Override
	@Transactional
	public OrdenPizza save(OrdenPizza orden) {
		
		//Defino el prepared statement de la consulta
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into OrdenPizza "
				+ "(nombrePersona, ciudad, barrio, direccion, nroTarjeta, fecVencimiento, codigoCVV) "
				+ "values (?,?,?,?,?,?,?)", Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR);
		
		//Para obtener el id autogenerado por la BD al salvar OrdenPizza
		pscf.setReturnGeneratedKeys(true);
		
		//Al prepared statement le seteo los params
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
				orden.getNombrePersona(), orden.getCiudad(), orden.getBarrio(), orden.getDireccion(), orden.getNroTarjeta(), orden.getFecVencimiento(), orden.getCodigoCVV()));
		
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); 
		
		//Ejecuto la consulta
		jdbcOperations.update(psc, keyHolder);
		
		//Obtengo el ID que le asigno la BD al registro recien ingresado
		long orderId = keyHolder.getKey().longValue(); 
		
		//Seteo el Id que le asigno la BD a la orden
		orden.setId(orderId);
		
		//Recorro la colección de pizzas y las salvo a la BD
		orden.getPizzas().forEach(p -> savePizza(orderId, p));
		
		return orden;
	}
	
	/**
	 * Metodo responsable de persistir una pizza asociada a una orden previamente existente en la BD
	 * @param ordenId N° de la OrdenPizza en la BD
	 * @param pizza pizza a persistir
	 * @return Pizza con id asignado 
	 */
	private Pizza savePizza(Long ordenId, Pizza pizza) {
		PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
				"insert into Pizza "
				+ "(nombre, fk_ordenPizza_id) "
				+ "values (?,?)", Types.VARCHAR, Type.LONG);
		
		pscf.setReturnGeneratedKeys(true);
		
		PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(pizza.getNombre(),ordenId));
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); jdbcOperations.update(psc, keyHolder);

		pizza.setId(keyHolder.getKey().longValue());

		saveIngredientes(pizza.getId(), pizza.getIngredientes());
		
		return pizza;
		
	}
	
	private void saveIngredientes(Long pizzaId, List<IngredientePizza> ingredientes) {
		ingredientes.forEach(i -> {
			jdbcOperations.update("insert into IngredientePizza (fk_ingrediente_id, fk_pizza_id) values (?, ?)", i.getIngrediente(), pizzaId);
		});
		
	}

}
