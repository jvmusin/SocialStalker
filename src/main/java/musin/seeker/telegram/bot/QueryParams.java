package musin.seeker.telegram.bot;

import lombok.Data;
import lombok.experimental.Delegate;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Data
public class QueryParams {
  @Delegate
  private final AbsSender absSender;
  private final User user;
  private final Chat chat;
  private final Integer messageId;
  private final String[] arguments;

  public Long getChatId() {
    return chat.getId();
  }
}
