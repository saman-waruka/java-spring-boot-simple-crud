package link.samwar.simple_crud;

import io.github.cdimascio.dotenv.Dotenv;
import link.samwar.simple_crud.entities.Person;
import link.samwar.simple_crud.repositories.PersonDAO;
import org.ajbrown.namemachine.Gender;
import org.ajbrown.namemachine.NameGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SimpleCrudApplication {

	public static void main(String[] args) {
		// Load .env file
		Dotenv dotenv = Dotenv.load();
		System.out.printf("Dotenv %s",dotenv.entries().toString());



		// Set System properties
		System.setProperty("spring.datasource.url", dotenv.get("SPRING_DATASOURCE_URL"));
		System.setProperty("spring.datasource.username", dotenv.get("SPRING_DATASOURCE_USERNAME"));
		System.setProperty("spring.datasource.password", dotenv.get("SPRING_DATASOURCE_PASSWORD"));

		SpringApplication.run(SimpleCrudApplication.class, args);
	}




	@Bean
	public CommandLineRunner commandLineRunner(PersonDAO dao){
		return runner->{
			System.out.println("Hello World!");

			System.out.println("Start inserting...");
			insertPerson(dao);
			System.out.println("Inserted succeed.");

			System.out.println("Removing ");
			removePerson( UUID.fromString("1ED8315F-DC02-49A8-B4EB-E3227763DC6A"), dao);

			System.out.println("Retrieving by id");
			Person someone = findPersonById(UUID.fromString("B2EBDAA6-BDDC-4352-AE81-F93F35727AB5"), dao);


			System.out.println("Find all person");
			// Print table headers
			System.out.printf("%-36s %-5s %-15s%n", "id", "firstname", "lastname");
			System.out.println("-------------------------------------------------------------");
			List<Person> persons = findAllPerson(dao);
			// Print each person in the list as a row in the table
			for (Person person : persons) {
				//System.out.printf("%-36s %-15s %-15s%n", person.getId(), person.getFname(), person.getLname());
				System.out.println(person);
			}
		};
	}

	public void insertPerson(PersonDAO dao){
		NameGenerator generator = new NameGenerator();
		Gender gender = Gender.MALE ;
		if(Math.random()>0.5){
			gender = Gender.FEMALE;
		}
		String fullname = generator.generateName(gender).toString();
		String[] nameParts = fullname.split(" ");

		Person person = new Person(nameParts[0],nameParts[1]);
		dao.save(person);
	}

	public void removePerson(UUID id, PersonDAO dao){
		dao.delete(id);
	}

	public Person findPersonById(UUID id,PersonDAO dao){
        return dao.findById(id);
	}

	public List<Person> findAllPerson(PersonDAO dao){
		return dao.findAll();
	}
}
