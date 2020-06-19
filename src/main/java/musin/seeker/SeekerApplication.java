package musin.seeker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.telegram.telegrambots.ApiContextInitializer;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaRepositories
@SpringBootApplication
public class SeekerApplication {
  public static void main(String[] args) {
    ApiContextInitializer.init();
    SpringApplication.run(SeekerApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Samara"));
  }
}
