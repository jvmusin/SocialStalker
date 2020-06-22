package musin.socialstalker.telegram.bot.command;

import musin.socialstalker.telegram.bot.Session;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface Command {
  String getName();

  String getDescription();

  void handle(Session session, Update update, AbsSender sender);
}
