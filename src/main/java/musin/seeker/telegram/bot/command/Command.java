package musin.seeker.telegram.bot.command;

import musin.seeker.telegram.bot.Session;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface Command {
  String getName();

  void handle(Session session, Update update, AbsSender sender);
}
