package musin.seeker.telegram.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@RequiredArgsConstructor
public class TelegramMessageSenderImpl implements TelegramMessageSender {

  private final long receiverUid;
  private final String botToken;

  private final RestTemplate restTemplate;
  private final AsyncListenableTaskExecutor taskExecutor;

  @Override
  @SneakyThrows
  public void sendMessage(@NotNull String message, boolean waitForExecutionEnd) {
    ListenableFuture<?> task = taskExecutor.submitListenable(() -> {
      SendMessage m = new SendMessage(receiverUid, message);
      m.setParseMode("Markdown");
      String url = String.format("https://api.telegram.org/bot%s/sendMessage", botToken);
      try {
        restTemplate.postForEntity(url, m, Message.class);
      } catch (RestClientException e) {
        e.printStackTrace();
      }
    });
    if (waitForExecutionEnd) task.get();
  }
}
