package musin.seeker.telegram.bot;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import musin.seeker.telegram.bot.command.Command;
import musin.seeker.telegram.bot.command.HelpCommand;
import musin.seeker.telegram.config.TelegramConfigurationProperties;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class Tebot extends TelegramLongPollingBot {

  private final TelegramConfigurationProperties config;
  private final Map<String, Command> commands;
  private final Map<Integer, Session> sessions = new ConcurrentHashMap<>();

  @SneakyThrows
  public Tebot(TelegramConfigurationProperties config, Map<String, Command> commands) {
    this.config = config;
    this.commands = commands;
    commands.put(HelpCommand.NAME, new HelpCommand(commands));
  }

  @Override
  @SneakyThrows
  public void onUpdateReceived(Update update) {
    if (!update.hasMessage()) return;

    String text = update.getMessage().getText();
    Integer userId = update.getMessage().getFrom().getId();
    Session session = sessions.get(userId);

    try {
      Command command = commands.get(text.split(" ")[0].toLowerCase());
      if (command != null) {
        sessions.put(userId, session = new Session(userId));
        command.handle(session, update, this);
        return;
      }

      if (session != null) {
        commands.get(session.getCommand()).handle(session, update, this);
        return;
      }

      handleHelp(update);
    } catch (Exception e) {
      log.throwing(Level.WARN, e);
      execute(new SendMessage(update.getMessage().getChatId(), e.getMessage()));
      handleHelp(update);
      if (session != null) sessions.remove(userId);
    } finally {
      if (session != null && session.isDone()) sessions.remove(userId);
    }
  }

  private void handleHelp(Update update) {
    commands.get(HelpCommand.NAME).handle(null, update, this);
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
