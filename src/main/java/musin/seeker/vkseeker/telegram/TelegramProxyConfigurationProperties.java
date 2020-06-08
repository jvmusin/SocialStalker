package musin.seeker.vkseeker.telegram;

import lombok.Data;

import java.net.Proxy;

@Data
public class TelegramProxyConfigurationProperties {
  private boolean enabled;
  private Proxy.Type type;
  private String hostname;
  private int port;
}
