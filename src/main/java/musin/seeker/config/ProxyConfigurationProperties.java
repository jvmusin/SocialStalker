package musin.seeker.config;

import lombok.Data;

import java.net.Proxy;

@Data
public class ProxyConfigurationProperties {
  private boolean enabled;
  private Proxy.Type type;
  private String hostname;
  private int port;
}
