package musin.socialstalker.telegram.bot;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import musin.socialstalker.db.service.StalkerService;
import musin.socialstalker.telegram.bot.command.Command;
import musin.socialstalker.telegram.bot.command.HelpCommand;
import musin.socialstalker.telegram.config.TelegramConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
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
        if (command != helpCommand) {
          sessions.put(userId, session = new Session(stalkerService.get(message.getChatId())));
        } else {
          sessions.remove(userId);
        }
        command.handle(session, update, this);
        return;
      }

      if (session != null) {
        commands.get(session.getCommand()).handle(session, update, this);
        return;
      }

      handleHelp(update);
    } catch (Exception e) {
      log.warn("Can't handle a telegram message", e);
      execute(new SendMessage(message.getChatId(), e.getMessage()));
      handleHelp(update);
      if (session != null) sessions.remove(userId);
    } finally {
      if (session != null && session.isDone()) {
        sessions.remove(userId);
        handleHelp(update);
      }
    }
  }

  private void handleHelp(Update update) {
    helpCommand.handle(null, update, this);
  }

  @Override
  public String getBotUsername() {
    return "Sostalker";
  }
}
