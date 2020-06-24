package musin.socialstalker.notifier;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsoleMessageSender implements MessageSender {
  @Override
  public void sendMessage(String text) {
    log.info(text);
  }
}
