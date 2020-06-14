package musin.seeker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class SeekerApplication {
  public static void main(String[] args) {
    SpringApplication.run(SeekerApplication.class, args);
  }
}
