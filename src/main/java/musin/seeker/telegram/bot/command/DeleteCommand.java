package musin.seeker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.seeker.relation.User;
import musin.seeker.telegram.bot.Session;
import musin.seeker.telegram.bot.service.Service;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Map;
import java.util.Optional;

@Component(DeleteCommand.NAME)
public class DeleteCommand extends TypicalServiceCommand {

  public static final String NAME = "/delete";

  public DeleteCommand(Map<String, Service> services) {
    super(services);
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Service service = getService(session.getService());
    String usernameOrId = update.getMessage().getText();
    Optional<User<?>> user = service.searchByUsernameOrId(usernameOrId);
    if (user.isPresent()) {
      service.delete(user.get().getId().toString());
      session.setDone(true);
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " successfully deleted"));
    } else {
      sender.execute(new SendMessage(update.getMessage().getChatId(), "User " + usernameOrId + " not found"));
    }
  }
}
