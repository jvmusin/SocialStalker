package musin.seeker.telegram.bot;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import musin.seeker.db.StalkerService;
import musin.seeker.telegram.bot.command.Command;
import musin.seeker.telegram.bot.command.HelpCommand;
import musin.seeker.telegram.config.TelegramConfigurationProperties;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@Component
public class Tebot extends TelegramLongPollingBot {

  @Getter
  private final String botToken;
  private final Map<String, Command> commands;
  private final Map<Integer, Session> sessions = new ConcurrentHashMap<>();
  private final StalkerService stalkerService;
  private final HelpCommand helpCommand;

  @SneakyThrows
  public Tebot(TelegramConfigurationProperties config, Map<String, Command> commands, StalkerService stalkerService) {
    this.botToken = config.getBotToken();
    this.commands = commands;
    this.stalkerService = stalkerService;
    commands.put(HelpCommand.NAME, this.helpCommand = new HelpCommand(commands));
  }

  @Override
  @SneakyThrows
  public void onUpdateReceived(Update update) {
    if (!update.hasMessage()) return;
    Message message = update.getMessage();

    if (!message.getChat().isUserChat()) {
      execute(new SendMessage(message.getChatId(), "The bot doesn't work in groups and chats"));
      return;
    }

    String text = message.getText();
    Integer userId = message.getFrom().getId();
    Session session = sessions.get(userId);

    try {
      Command command = commands.get(text.split(" ")[0].toLowerCase());
      if (command != null) {
        sessions.put(userId, session = new Session(stalkerService.get(message.getChatId())));
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
      execute(new SendMessage(message.getChatId(), e.getMessage()));
      handleHelp(update);
      if (session != null) sessions.remove(userId);
    } finally {
      if (session != null && session.isDone()) sessions.remove(userId);
    }
  }

  private void handleHelp(Update update) {
    helpCommand.handle(null, update, this);
  }

  @Override
  public String getBotUsername() {
    return "Social Stalker";
  }
}
