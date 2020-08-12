package musin.stalker.telegram.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import musin.stalker.telegram.api.MarkdownSendMessage;
import musin.stalker.telegram.bot.Session;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;

import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
public class HelpCommand implements Command {

  public static final String NAME = "/help";

  private final Map<String, Command> commands;

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return "show help";
  }

  @Override
  @SneakyThrows
  public void handle(Session session, Update update, AbsSender sender) {
    String ls = System.lineSeparator();
    String text = commands.values().stream()
        .map(c -> c.getName() + " - " + c.getDescription())
        .collect(joining(ls, "Available commands are:" + ls + ls, ""));
    SendMessage method = new MarkdownSendMessage(update.getMessage().getChatId(), text);
    method.setReplyMarkup(new ReplyKeyboardRemove());
    sender.execute(method);
  }
}
