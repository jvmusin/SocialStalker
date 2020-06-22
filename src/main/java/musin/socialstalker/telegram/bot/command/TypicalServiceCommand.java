package musin.socialstalker.telegram.bot.command;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.telegram.bot.Session;
import musin.socialstalker.telegram.bot.service.Network;
import musin.socialstalker.telegram.bot.service.NetworkFactory;
import musin.socialstalker.telegram.bot.service.NoSuchNetworkException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class TypicalServiceCommand implements Command {

  private final Map<String, NetworkFactory> networkFactories;

  @Override
  public void handle(Session session, Update update, AbsSender sender) {
    if (session.getCommand() == null) {
      handleSetCommand(session, update, sender);
      return;
    }
    if (session.getService() == null) {
      handleSetService(session, update, sender);
      return;
    }
    handleFinish(session, update, sender);
  }

  @SneakyThrows
  protected void handleSetCommand(Session session, Update update, AbsSender sender) {
    session.setCommand(getName());

    SendMessage sendMessage = new SendMessage(update.getMessage().getChatId(), "Select a network");

    List<KeyboardRow> rows = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    networkFactories.keySet().forEach(row::add);
    rows.add(row);

    ReplyKeyboardMarkup kb = new ReplyKeyboardMarkup(rows);
    kb.setOneTimeKeyboard(true);
    kb.setResizeKeyboard(true);
    sendMessage.setReplyMarkup(kb);

    sender.execute(sendMessage);
  }

  @SneakyThrows
  protected void handleSetService(Session session, Update update, AbsSender sender) {
    String serviceName = update.getMessage().getText().toUpperCase();
    getNetwork(serviceName, session.getStalker());
    session.setService(serviceName);

    sender.execute(new SendMessage(update.getMessage().getChatId(), "Enter username or user id"));
  }

  protected abstract void handleFinish(Session session, Update update, AbsSender sender);

  protected Network getNetwork(String name, Stalker stalker) {
    NetworkFactory networkFactory = networkFactories.get(name);
    if (networkFactory == null) throw new NoSuchNetworkException("No such network: " + name);
    return networkFactory.create(stalker);
  }
}
