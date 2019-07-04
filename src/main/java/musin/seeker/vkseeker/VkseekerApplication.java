package musin.seeker.vkseeker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.TimeZone;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class VkseekerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VkseekerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("191.252.185.161", 8090));
        requestFactory.setProxy(proxy);

        return new RestTemplate(requestFactory);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Samara"));
    }
}