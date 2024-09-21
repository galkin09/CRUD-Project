package proj.dao;

import proj.models.Book;
import proj.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person findById(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into Person(name, middlename, surname, age) values(?,?,?,?)", person.getName(),
        person.getMiddleName(),person.getSurname(),person.getAge());
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update Person set name=?, middlename=?, surname=?, age=? where id=?", updatedPerson.getName(),
                updatedPerson.getMiddleName(),updatedPerson.getSurname(),updatedPerson.getAge(),id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where id=?", id);
    }

    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM Books WHERE person_id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}
