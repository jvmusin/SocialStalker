package musin.seeker.telegram.bot;

import lombok.SneakyThrows;
import musin.seeker.telegram.config.TelegramConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Tebot extends TelegramLongPollingBot {

  private final TelegramConfigurationProperties config;

  @SneakyThrows
  public Tebot(TelegramConfigurationProperties config) {
    this.config = config;
  }

  @Override
  public void onUpdateReceived(Update update) {
    System.err.println(update);
  }

  @Override
  public String getBotUsername() {
    return "VkSeeker";
  }

  @Override
  public String getBotToken() {
    return config.getBotToken();
  }
}
