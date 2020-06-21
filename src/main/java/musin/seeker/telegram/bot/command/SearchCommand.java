package musin.seeker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.seeker.relation.User;
import musin.seeker.telegram.api.MarkdownSendMessage;
import musin.seeker.telegram.bot.Session;
import musin.seeker.telegram.bot.service.Service;
import musin.seeker.telegram.bot.service.ServiceFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(SearchCommand.NAME)
public class SearchCommand extends TypicalServiceCommand {

  public static final String NAME = "/search";

  public SearchCommand(Map<String, ServiceFactory> serviceFactories) {
    super(serviceFactories);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Service service = getService(session.getService(), session.getStalker());
    String username = update.getMessage().getText();
    Optional<User<?>> user = service.searchByUsername(username);
    session.setDone(true);

    String text = user.map(u -> "Found user " + u.getMarkdownLink())
        .orElse("User " + username + " not found");
    sender.execute(new MarkdownSendMessage(update.getMessage().getChatId(), text));
  }
}
