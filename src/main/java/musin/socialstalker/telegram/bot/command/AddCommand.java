package musin.socialstalker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.socialstalker.relation.User;
import musin.socialstalker.telegram.bot.Session;
import musin.socialstalker.telegram.bot.service.Network;
import musin.socialstalker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(AddCommand.NAME)
public class AddCommand extends TypicalServiceCommand {

  public static final String NAME = "/add";

  public AddCommand(Map<String, NetworkFactory> networkFactories) {
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
    String usernameOrId = update.getMessage().getText();
    Optional<User<?>> user = network.searchByUsernameOrId(usernameOrId);
    if (user.isPresent()) {
      network.add(user.get().getId().toString());
      session.setDone(true);
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " successfully added"));
    } else {
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " not found"));
    }
  }
}
