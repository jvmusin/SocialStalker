package musin.seeker.vkseeker.notifiers;

import lombok.extern.log4j.Log4j2;
import musin.seeker.vkseeker.api.VkApi;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ConsoleChangesNotifier extends ChangesNotifierBase {

  public ConsoleChangesNotifier(VkApi vkApi) {
    super(vkApi);
  }

  @Override
  public void sendMessage(String message) {
    log.info(message);
  }
}
