package musin.seeker.vkseeker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class VkseekerApplication {
  public static void main(String[] args) {
    SpringApplication.run(VkseekerApplication.class, args);
  }
}
