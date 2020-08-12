package musin.stalker.telegram.bot.command;

import musin.stalker.telegram.bot.Session;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface Command {
  String getName();

  String getDescription();

  void handle(Session session, Update update, AbsSender sender);
}
