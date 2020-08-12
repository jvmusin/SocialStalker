package musin.stalker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.stalker.telegram.bot.Session;
import musin.stalker.relation.User;
import musin.stalker.telegram.api.MarkdownSendMessage;
import musin.stalker.telegram.bot.service.Network;
import musin.stalker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(FindCommand.NAME)
public class FindCommand extends TypicalNetworkCommand {

  public static final String NAME = "/find";

  public FindCommand(Map<String, NetworkFactory> networkFactories) {
    super(networkFactories);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return "find a user by username or user id";
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Network network = getNetwork(session.getService(), session.getStalker());
    String username = update.getMessage().getText();
    Optional<User> user = network.searchByUsername(username);
    session.setDone(true);

    String text = user.map(u -> "Found user " + u.getMarkdownLink())
        .orElse("User " + username + " not found");
    sender.execute(new MarkdownSendMessage(update.getMessage().getChatId(), text));
  }
}
