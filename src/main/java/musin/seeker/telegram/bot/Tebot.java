package musin.seeker.telegram.bot;

import lombok.SneakyThrows;
import musin.seeker.telegram.config.TelegramConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class Tebot extends TelegramLongPollingCommandBot {

  private final TelegramConfigurationProperties config;

  @SneakyThrows
  public Tebot(TelegramConfigurationProperties config, List<IBotCommand> commands) {
    this.config = config;
    commands.forEach(this::register);
  }

  @Override
  @SneakyThrows
  public void processNonCommandUpdate(Update update) {
    execute(new SendMessage(update.getMessage().getChatId(), "Unknown command"));
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
