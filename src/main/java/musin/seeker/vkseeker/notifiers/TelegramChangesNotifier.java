package musin.seeker.vkseeker.notifiers;

import lombok.SneakyThrows;
import musin.seeker.vkseeker.api.VkApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestClientException;
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
  private final AsyncListenableTaskExecutor taskExecutor;

  public TelegramChangesNotifier(VkApi vkApi, @Qualifier("tgRestTemplate") RestTemplate restTemplate, AsyncListenableTaskExecutor taskExecutor) {
    super(vkApi);
    this.restTemplate = restTemplate;
    this.taskExecutor = taskExecutor;
  }

  @Override
  protected void sendMessage(String message) {
    sendMessageAsync(message, false);
  }

  @SneakyThrows
  private void sendMessageAsync(String message, boolean waitForExecutionEnd) {
    ListenableFuture<?> task = taskExecutor.submitListenable(() -> {
      SendMessage m = new SendMessage(MUSIN_UID, message);
      m.setParseMode("Markdown");
      String url = String.format("https://api.telegram.org/bot%s/sendMessage", BOT_TOKEN);
      try {
        restTemplate.postForEntity(url, m, Message.class);
      } catch (RestClientException e) {
        e.printStackTrace();
      }
    });
    if (waitForExecutionEnd) task.get();
  }

  @PostConstruct
  public void init() {
    sendMessageAsync("I'm alive", false);
  }

  @PreDestroy
  public void shutdown() {
    sendMessageAsync("I'm shutting down", true);
  }
}
