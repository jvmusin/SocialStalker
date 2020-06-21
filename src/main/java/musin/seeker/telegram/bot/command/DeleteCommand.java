package musin.seeker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.seeker.relation.User;
import musin.seeker.telegram.bot.Session;
import musin.seeker.telegram.bot.service.Network;
import musin.seeker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(DeleteCommand.NAME)
public class DeleteCommand extends TypicalServiceCommand {

  public static final String NAME = "/delete";

  public DeleteCommand(Map<String, NetworkFactory> networkFactories) {
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
      network.delete(user.get().getId().toString());
      session.setDone(true);
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " successfully deleted"));
    } else {
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " not found"));
    }
  }
}
