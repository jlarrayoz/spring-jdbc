package edu.curso.models.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import edu.curso.domain.Ingrediente;
import edu.curso.domain.TipoIngrediente;
import edu.curso.models.IngredienteRepository;

@Repository
public class IngredienteJdbcRepository implements IngredienteRepository {

	//Clase de spring para simplificar el acceso a la BD por JDBC
	private JdbcTemplate jdbcTemplate;
	
	
	
	public IngredienteJdbcRepository(JdbcTemplate jdbcTempalte) {
		this.jdbcTemplate = jdbcTempalte;
	}

	@Override
	public List<Ingrediente> findAll() {
		return jdbcTemplate.query("select id, nombre, tipo from Ingrediente order by id asc", this::mapRowToIngrediente);
	}

	@Override
	public Optional<Ingrediente> findById(String id) {
		List<Ingrediente> resultado = jdbcTemplate.query("select id, nombre, tipo from Ingrediente where id = ?", this::mapRowToIngrediente, id);
		
		return resultado.size() == 0 ? Optional.empty() : Optional.of(resultado.get(0));
				
	}

	@Override
	public Ingrediente save(Ingrediente ingrediente) {
		jdbcTemplate.update("insert into Ingrediente (id, nombre, tipo) values (?, ?, ?)", ingrediente.getId(), ingrediente.getNombre(), ingrediente.getTipo().toString());
		
		return ingrediente;
	}
	
	/**
	 * Método encargado de convertir el resultset en un ingrediente
	 * @param row row del resultset
	 * @param rowNum n° de row
	 * @return Ingrediente con los datos extraidos del row
	 * @throws SQLException si ocurre algún error
	 */
	private Ingrediente mapRowToIngrediente(ResultSet row, int rowNum) throws SQLException {
		return new Ingrediente(row.getString("id"), row.getString("nombre"), TipoIngrediente.valueOf(row.getString("tipo")));
	}

}
