package musin.socialstalker.notifier;

import lombok.extern.log4j.Log4j2;
import musin.socialstalker.relation.User;

@Log4j2
public class ConsoleUpdateNotifier<
    TUpdate extends NotifiableUpdate<? extends User<?>, ?>>
    extends MarkdownUpdateNotifier<TUpdate> {

  public ConsoleUpdateNotifier() {
    super(new ConsoleMessageSender());
  }
}
