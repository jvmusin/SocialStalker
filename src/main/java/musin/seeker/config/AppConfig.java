package musin.seeker.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;
import java.util.concurrent.ForkJoinPool;

@Log4j2
@Configuration
@EnableConfigurationProperties
public class AppConfig {

  @PostConstruct
  public void init() {
    log.info("FJP parallelism is " + ForkJoinPool.getCommonPoolParallelism());
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Samara"));
  }
}
