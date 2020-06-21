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
import java.util.stream.Collectors;

@Component(ListCommand.NAME)
public class ListCommand extends TypicalServiceCommand {

  public static final String NAME = "/list";

  public ListCommand(Map<String, NetworkFactory> networkFactories) {
    super(networkFactories);
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

    String networkName = update.getMessage().getText().toUpperCase();
    Network network = getNetwork(networkName, session.getStalker());

    session.setDone(true);

    String ls = System.lineSeparator();
    String text = network.listTargets().stream()
        .map(User::getMarkdownLink)
        .collect(Collectors.joining(ls, "Found targets:" + ls, ""));
    sender.execute(new MarkdownSendMessage(update.getMessage().getChatId(), text));
  }

  @Override
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    throw new IllegalStateException("Impossible to get here");
  }
}
