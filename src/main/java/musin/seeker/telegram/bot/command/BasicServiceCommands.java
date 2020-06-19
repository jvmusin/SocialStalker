package musin.seeker.telegram.bot.command;

import lombok.SneakyThrows;
import musin.seeker.db.IdFactory;
import musin.seeker.updater.SeekerService;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class BasicServiceCommands<ID> extends DefaultBotCommand {

  private final SeekerService<ID> seekerService;
  private final IdFactory<ID> idFactory;

  public BasicServiceCommands(String serviceName, SeekerService<ID> seekerService, IdFactory<ID> idFactory) {
    super(serviceName, "basic commands for " + serviceName);
    this.seekerService = seekerService;
    this.idFactory = idFactory;
  }

  @Override
  @SneakyThrows
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    try {
      String type = arguments[0].toLowerCase();
      ID id = idFactory.parse(arguments[1]);
      switch (type) {
        case "add" -> handleAdd(id, absSender, chat.getId());
        case "delete" -> handleDelete(id, absSender, chat.getId());
        default -> handleUnknown(type, absSender, chat.getId());
      }
    } catch (Exception e) {
      absSender.execute(new SendMessage(chat.getId(), e.getMessage()));
    }
  }

  @SneakyThrows
  private void handleAdd(ID id, AbsSender sender, Long chatId) {
    seekerService.createSeeker(id);
    sender.execute(new SendMessage(chatId, "Seeker for " + id + " successfully added"));
  }

  @SneakyThrows
  private void handleDelete(ID id, AbsSender sender, Long chatId) {
    seekerService.deleteSeeker(id);
    sender.execute(new SendMessage(chatId, "Seeker for" + id + " successfully deleted"));
  }

  @SneakyThrows
  private void handleUnknown(String command, AbsSender sender, Long chatId) {
    sender.execute(new SendMessage(chatId, "Unknown command " + command));
  }
}
