package musin.socialstalker.telegram.config;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.telegram.api.TelegramMessageSender;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
@Profile("telegram")
@RequiredArgsConstructor
public class TelegramStatusNotifier {
  private final TelegramMessageSender sender;

  @EventListener(ApplicationReadyEvent.class)
  public void sayHello() {
    sender.sendMessage("I'm alive!", false);
  }

  @PreDestroy
  public void sayGoodbye() {
    sender.sendMessage("I'm shutting down", true);
  }
}
