package musin.seeker.telegram.bot.command;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import musin.seeker.api.SocialApi;
import musin.seeker.db.IdFactory;
import musin.seeker.relation.UserFactory;
import musin.seeker.telegram.api.MarkdownSendMessage;
import musin.seeker.telegram.bot.QueryParams;
import musin.seeker.updater.SeekerService;
import org.apache.logging.log4j.Level;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.DefaultBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

@Log4j2
public class BasicServiceCommands<ID, TUser extends musin.seeker.relation.User<ID>> extends DefaultBotCommand {

  private final SeekerService<ID> seekerService;
  private final IdFactory<ID> idFactory;
  private final UserFactory<ID, TUser> userFactory;
  private final SocialApi<ID> api;

  public BasicServiceCommands(String serviceName,
                              SeekerService<ID> seekerService,
                              IdFactory<ID> idFactory,
                              UserFactory<ID, TUser> userFactory,
                              SocialApi<ID> api) {
    super(serviceName, "basic commands for " + serviceName);
    this.seekerService = seekerService;
    this.idFactory = idFactory;
    this.userFactory = userFactory;
    this.api = api;
  }

  @Override
  @SneakyThrows
  public void execute(AbsSender absSender, User user, Chat chat, Integer messageId, String[] arguments) {
    QueryParams params = new QueryParams(absSender, user, chat, messageId, arguments);
    try {
      String type = arguments[0].toLowerCase();
      switch (type) {
        case "add" -> handleAdd(params);
        case "delete" -> handleDelete(params);
        case "list" -> handleList(params);
        case "search" -> handleSearch(params);
        default -> handleUnknown(type, params);
      }
    } catch (Exception e) {
      log.throwing(Level.WARN, e);
      absSender.execute(new SendMessage(chat.getId(), e.getMessage()));
    }
  }

  @SneakyThrows
  private void handleAdd(QueryParams params) {
    ID id = idFactory.parse(params.getArguments()[1]);
    seekerService.createSeeker(id);
    params.execute(new SendMessage(params.getChatId(), "Seeker for " + id + " successfully added"));
  }

  @SneakyThrows
  private void handleDelete(QueryParams params) {
    ID id = idFactory.parse(params.getArguments()[1]);
    seekerService.deleteSeeker(id);
    params.execute(new SendMessage(params.getChatId(), "Seeker for" + id + " successfully deleted"));
  }

  @SneakyThrows
  private void handleUnknown(String command, QueryParams params) {
    params.execute(new SendMessage(params.getChatId(), "Unknown command " + command));
  }

  @SneakyThrows
  private void handleList(QueryParams params) {
    String owners = seekerService.findAllOwners().stream()
        .map(userFactory::create)
        .map(Object::toString)
        .collect(joining(lineSeparator()));
    String text = "Seekers for " + getCommandIdentifier() + lineSeparator() + owners;
    params.execute(new SendMessage(params.getChatId(), text));
  }

  @SneakyThrows
  private void handleSearch(QueryParams params) {
    String username = params.getArguments()[1];
    ID id = api.searchByUsername(username);
    if (id == null) {
      params.execute(new SendMessage(params.getChatId(), "User " + username + " not found"));
    } else {
      TUser user = userFactory.create(id);
      params.execute(new MarkdownSendMessage(params.getChatId(), "Found user " + user.getMarkdownLink()));
    }
  }
}
