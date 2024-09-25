package link.samwar.simple_crud.repositories;

import link.samwar.simple_crud.entities.Person;

import java.util.List;
import java.util.UUID;

public interface PersonDAO {
    void save(Person person);
    void delete(UUID id);
    Person findById(UUID id);
    List<Person> findAll();
}
