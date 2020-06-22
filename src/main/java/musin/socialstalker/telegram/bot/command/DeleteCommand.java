package musin.socialstalker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.socialstalker.relation.User;
import musin.socialstalker.telegram.api.MarkdownSendMessage;
import musin.socialstalker.telegram.bot.Session;
import musin.socialstalker.telegram.bot.service.Network;
import musin.socialstalker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(DeleteCommand.NAME)
public class DeleteCommand extends TypicalNetworkCommand {

  public static final String NAME = "/delete";

  public DeleteCommand(Map<String, NetworkFactory> networkFactories) {
    super(networkFactories);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return "Add a target for stalkering";
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Network network = getNetwork(session.getService(), session.getStalker());
    String usernameOrId = update.getMessage().getText();
    Optional<User<?>> user = network.searchByUsernameOrId(usernameOrId);
    if (user.isPresent()) {
      if (network.deleteMonitoring(user.get().getId().toString())) {
        sender.execute(new MarkdownSendMessage(
            update.getMessage().getChatId(),
            "User " + user.get().getMarkdownLink() + " successfully deleted!"
        ));
      } else {
        sender.execute(new MarkdownSendMessage(
            update.getMessage().getChatId(),
            "User " + user.get().getMarkdownLink() + " not deleted. Probably the target didn't exist."
        ));
      }
    } else {
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " not found"));
    }
    session.setDone(true);
  }
}
