package musin.stalker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.stalker.relation.User;
import musin.stalker.telegram.api.MarkdownSendMessage;
import musin.stalker.telegram.bot.Session;
import musin.stalker.telegram.bot.service.Network;
import musin.stalker.telegram.bot.service.NetworkFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    return "delete a target for stalkering";
  }

  @Override
  @SneakyThrows
  protected void handleSetService(Session session, Update update, AbsSender sender) {
    String serviceName = update.getMessage().getText().toUpperCase();
    getNetwork(serviceName, session.getStalker());
    session.setService(serviceName);

    SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(), "Enter username or id");
    Network network = getNetwork(session.getService(), session.getStalker());
    List<KeyboardRow> rows = network.listTargets().stream()
        .map(User::getFullyQualifiedName)
        .map(KeyboardButton::new)
        .map(this::createKeyboardRow)
        .collect(Collectors.toList());
    if (rows.isEmpty()) {
      session.setDone(true);
      sender.execute(new SendMessage(update.getMessage().getChatId(), "Nobody to delete!"));
      return;
    }
    ReplyKeyboardMarkup kb = new ReplyKeyboardMarkup(rows);
    kb.setOneTimeKeyboard(true);
    kb.setResizeKeyboard(true);
    sendMessage.setReplyMarkup(kb);

    sender.execute(sendMessage);
  }

  private KeyboardRow createKeyboardRow(KeyboardButton button) {
    KeyboardRow row = new KeyboardRow();
    row.add(button);
    return row;
  }

  @Override
  @SneakyThrows
  protected void handleFinish(Session session, Update update, AbsSender sender) {
    Network network = getNetwork(session.getService(), session.getStalker());
    String usernameOrId = update.getMessage().getText().split(":")[0];
    Optional<User> user = network.searchByUsernameOrId(usernameOrId);
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
