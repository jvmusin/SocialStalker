package musin.stalker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.stalker.telegram.bot.Session;
import musin.stalker.relation.User;
import musin.stalker.telegram.api.MarkdownSendMessage;
import musin.stalker.telegram.bot.service.Network;
import musin.stalker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(AddCommand.NAME)
public class AddCommand extends TypicalNetworkCommand {

  public static final String NAME = "/add";

  public AddCommand(Map<String, NetworkFactory> networkFactories) {
    super(networkFactories);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return "add a new target for stalkering";
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Network network = getNetwork(session.getService(), session.getStalker());
    String usernameOrId = update.getMessage().getText();
    Optional<User> user = network.searchByUsernameOrId(usernameOrId);
    if (user.isPresent()) {
      if (network.addMonitoring(user.get().getId().toString())) {
        sender.execute(new MarkdownSendMessage(
            update.getMessage().getChatId(),
            "User " + user.get().getMarkdownLink() + " successfully added!"
        ));
      } else {
        sender.execute(new MarkdownSendMessage(
            update.getMessage().getChatId(),
            "User " + user.get().getMarkdownLink() + " not added. Probably already exists"
        ));
      }
    } else {
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " not found"));
    }
    session.setDone(true);
  }
}
