package musin.seeker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.seeker.relation.User;
import musin.seeker.telegram.api.MarkdownSendMessage;
import musin.seeker.telegram.bot.Session;
import musin.seeker.telegram.bot.service.Service;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.stream.Collectors;

@Component(ListCommand.NAME)
public class ListCommand extends TypicalServiceCommand {

  public static final String NAME = "/list";

  public ListCommand(Map<String, Service> services) {
    super(services);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @SneakyThrows
  protected void handleSetService(Session session, Update update, AbsSender sender) {
    if (session.getCommand() == null) {
      handleSetCommand(session, update, sender);
      return;
    }

    String serviceName = update.getMessage().getText().toUpperCase();
    Service service = getService(serviceName);

    session.setDone(true);

    String ls = System.lineSeparator();
    String text = service.listSeekers().stream()
        .map(User::getMarkdownLink)
        .collect(Collectors.joining(ls, "Found seekers:" + ls, ""));
    sender.execute(new MarkdownSendMessage(update.getMessage().getChatId(), text));
  }

  @Override
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    throw new IllegalStateException("Impossible to get here");
  }
}
