package ca.sheridancollege.kimdohee.database;

import java.util.List;

import ca.sheridancollege.kimdohee.beans.Coffee;
import ca.sheridancollege.kimdohee.beans.CoffeeSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;

	public void insertCoffeeHardCoded() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "INSERT INTO coffee (name, size, price, description) VALUES ('Latte','MEDIUM',4,'It is sweet')";
		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0)
			System.out.println("Hard coded Coffee inserted into the database");
	}

	public List<Coffee> getCoffeeList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM coffee";
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Coffee.class));
	}

	public void insertCoffee(Coffee coffee) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("name", coffee.getName())
				.addValue("size", coffee.getSize().toString())  // Assuming CoffeeSize is an enum
				.addValue("price", coffee.getPrice())
				.addValue("description", coffee.getDescription());

		String query = "INSERT INTO coffee (name, size, price, description) " +
				"VALUES (:name, :size, :price, :description)";

		int rowsAffected = jdbc.update(query, namedParameters);

		if (rowsAffected > 0) {
			System.out.println("Coffee inserted into the database");
		}
	}

	public void deleteCoffeeByName(String name) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM coffee WHERE name = :nameToDelete";
		namedParameters.addValue("nameToDelete", name);

		int rowsAffected = jdbc.update(query, namedParameters);
		if (rowsAffected > 0) {
			System.out.println("Coffee deleted from the database");
		}
	}

	public void deleteCoffeeById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM coffee WHERE id = :id";

		namedParameters.addValue("id", id);

		if (jdbc.update(query, namedParameters) > 0) {
			System.out.println("Deleted Coffee " + id + " from the database.");
		}
	}

	public List<Coffee> getCoffeeListById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM coffee WHERE id = :id";

		namedParameters.addValue("id", id);

		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Coffee.class));
	}

	// Rest of the methods...
}
