package musin.seeker.vkseeker.notifiers;

import musin.seeker.vkseeker.api.VkApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Profile("tg")
public class TelegramChangesNotifier extends ChangesNotifierBase {

  private static final long MUSIN_UID = 124275139;
  private static final String BOT_TOKEN = "809103789:AAFReBbzwDxrpVCFLJ2JZv2EKcaaAJuqP6o";

  private final RestTemplate restTemplate;
  private final TaskExecutor taskExecutor;

  public TelegramChangesNotifier(VkApi vkApi, @Qualifier("tgRestTemplate") RestTemplate restTemplate, TaskExecutor taskExecutor) {
    super(vkApi);
    this.restTemplate = restTemplate;
    this.taskExecutor = taskExecutor;
  }

  @Override
  public void sendMessage(String message) {
    taskExecutor.execute(() -> {
      SendMessage m = new SendMessage(MUSIN_UID, message);
      m.setParseMode("Markdown");
      String url = String.format("https://api.telegram.org/bot%s/sendMessage", BOT_TOKEN);
      restTemplate.postForEntity(url, m, Message.class);
    });
  }

  @PostConstruct
  public void init() {
    sendMessage("I'm alive");
  }

  @PreDestroy
  public void shutdown() {
    sendMessage("I'm shutting down");
  }
}
