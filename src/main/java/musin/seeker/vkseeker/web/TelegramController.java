package musin.seeker.vkseeker.web;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import musin.seeker.vkseeker.telegram.ChangesNotifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@RestController
@AllArgsConstructor
public class TelegramController {

  private final List<ChangesNotifier> notifiers;

  @RequestMapping("/telegram")
  @SneakyThrows
  public void ping(@RequestBody Update update) {
    notifiers.forEach(n -> n.sendMessage("yes, i'm here", update.getMessage().getChatId()));
  }

  @PostConstruct
  public void init() {
    notifiers.forEach(n -> n.sendMessage("I'm alive"));
  }

  @PreDestroy
  public void shutdown() {
    notifiers.forEach(n -> n.sendMessage("I'm shutting down"));
  }
}