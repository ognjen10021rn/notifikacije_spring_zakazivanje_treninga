package rs.ognjen_uros.notifikacije_spring_zakazivanje_treninga.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {


    public TestDataRunner() {

    }

    @Override
    public void run(String... args) throws Exception {

    }
}
