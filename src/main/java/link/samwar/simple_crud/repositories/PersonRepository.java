package link.samwar.simple_crud.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import link.samwar.simple_crud.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PersonRepository implements PersonDAO {

    private EntityManager entityManager;

    @Autowired
    public PersonRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Person person) {
        entityManager.persist(person);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
       Person person =  entityManager.find(Person.class, id);
       System.out.printf("Person: %s%n", person);
       if(person != null){
           entityManager.remove(person);
           System.out.printf("Removed: %s%n", id);
       } else {
           System.out.println("Person not found.");
       }
    }

    @Override
    public Person findById(UUID id) {
        Person person =  entityManager.find(Person.class, id);
        if(person != null){
            System.out.printf("Found: %s%n", person );
        } else {
            System.out.println("Person not found.");
        }

        return person;

    }

    @Override
    public List<Person> findAll() {
        TypedQuery<Person> personsQuery =  entityManager.createQuery("FROM Person", Person.class);
        return personsQuery.getResultList();
    }
}
