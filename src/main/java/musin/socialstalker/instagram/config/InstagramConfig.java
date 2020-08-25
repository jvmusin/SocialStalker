package musin.socialstalker.instagram.config;

import com.github.instagram4j.instagram4j.IGClient;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstagramConfig {
  @Bean
  @SneakyThrows
  public IGClient igClient(InstagramConfigurationProperties config) {
    return IGClient.builder()
        .username(config.getUsername())
        .password(config.getPassword())
        .login();
  }
}
