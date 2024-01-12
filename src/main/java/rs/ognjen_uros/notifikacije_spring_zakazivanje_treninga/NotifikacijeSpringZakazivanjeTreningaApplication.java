package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.service.EmailServiceImpl;

@SpringBootApplication
public class NotifikacijeSpringZakazivanjeTreningaApplication{

	public static void main(String[] args) {
		SpringApplication.run(NotifikacijeSpringZakazivanjeTreningaApplication.class, args);
	}
}
