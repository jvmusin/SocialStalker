package musin.socialstalker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.socialstalker.relation.User;
import musin.socialstalker.telegram.api.MarkdownSendMessage;
import musin.socialstalker.telegram.bot.Session;
import musin.socialstalker.telegram.bot.service.Network;
import musin.socialstalker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(SearchCommand.NAME)
public class SearchCommand extends TypicalServiceCommand {

  public static final String NAME = "/search";

  public SearchCommand(Map<String, NetworkFactory> networkFactories) {
    super(networkFactories);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Network network = getNetwork(session.getService(), session.getStalker());
    String username = update.getMessage().getText();
    Optional<User<?>> user = network.searchByUsername(username);
    session.setDone(true);

    String text = user.map(u -> "Found user " + u.getMarkdownLink())
        .orElse("User " + username + " not found");
    sender.execute(new MarkdownSendMessage(update.getMessage().getChatId(), text));
  }
}
