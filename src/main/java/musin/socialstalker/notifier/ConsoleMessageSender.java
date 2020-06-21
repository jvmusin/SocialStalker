package musin.socialstalker.notifier;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ConsoleMessageSender implements MessageSender {
  @Override
  public void sendMessage(@NotNull String text) {
    log.info(text);
  }
}
