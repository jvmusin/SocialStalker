package musin.stalker.notifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsoleUpdateNotifier extends MarkdownUpdateNotifier {
  public ConsoleUpdateNotifier() {
    super(new ConsoleMessageSender());
  }
}
