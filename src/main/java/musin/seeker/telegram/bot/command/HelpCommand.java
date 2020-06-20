package musin.seeker.telegram.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import musin.seeker.telegram.bot.Session;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.StringJoiner;

@RequiredArgsConstructor
public class HelpCommand implements Command {

  public static final String NAME = "/help";

  private final Map<String, Command> commands;

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  @SneakyThrows
  public void handle(Session session, Update update, AbsSender sender) {
    StringJoiner sj = new StringJoiner(System.lineSeparator());
    sj.add("Available commands are:" + System.lineSeparator());
    commands.keySet().forEach(sj::add);
    SendMessage method = new SendMessage(update.getMessage().getChatId(), sj.toString());
    method.setReplyMarkup(new ReplyKeyboardRemove());
    sender.execute(method);
  }
}
