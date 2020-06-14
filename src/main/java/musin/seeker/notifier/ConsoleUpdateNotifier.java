package musin.seeker.notifier;

import lombok.extern.log4j.Log4j2;
import musin.seeker.relation.User;
import org.jetbrains.annotations.NotNull;

@Log4j2
public class ConsoleUpdateNotifier<
    TUpdate extends NotifiableUpdate<? extends User, ?>>
    extends MarkdownUpdateNotifier<TUpdate> {

  @Override
  protected void sendMessage(@NotNull String message) {
    log.info(message);
  }
}
