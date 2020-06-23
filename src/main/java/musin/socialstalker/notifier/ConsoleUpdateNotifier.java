package musin.socialstalker.notifier;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleUpdateNotifier<TUpdate extends NotifiableUpdate<?>, TRelationType>
    extends MarkdownUpdateNotifier<TRelationType> {

  public ConsoleUpdateNotifier() {
    super(new ConsoleMessageSender());
  }
}
