package com.oluwafemi.demo.dao;

import com.oluwafemi.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
//@Repository ==> This is how we tell SpringBoot that this class should be instantiated as a Bean, so we can inject it in other classes
//("postgres") ==> This, together with @Qualifier("postgres) in the actual Service class (PersonService.java in this case) is
// how we tell SpringBoot what particular implementation of PerSonDao to use in PersonService - Dependency injection
public class PostgresPersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PostgresPersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        final String sql = "INSERT INTO person (id, name) VALUES (?, ?)";
        jdbcTemplate.update(sql, id, person.getName());
        return 1;
    }

    @Override
    public List<Person> getAllPeople() {
        final String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String name = resultSet.getString("name");
            return new Person(id, name);
        });
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {

        final String sql = "SELECT * FROM person WHERE id = ?";
        Person person = jdbcTemplate.queryForObject(
                sql,
                (resultSet, i) -> {
                    UUID personId = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    return new Person(personId, name);
                },
                id);
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {

        final String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {

        final String sql = "UPDATE person SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), id);
        return 1;

    }
}
