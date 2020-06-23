package musin.socialstalker.notifier;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleUpdateNotifier extends MarkdownUpdateNotifier {
  public ConsoleUpdateNotifier() {
    super(new ConsoleMessageSender());
  }
}
