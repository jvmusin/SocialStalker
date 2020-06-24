package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;
import org.springframework.stereotype.Component;

@Component
public class ConsoleUpdateNotifierFactory implements UpdateNotifierFactory {
  @Override
  public UpdateNotifier create(Stalker stalker) {
    return new ConsoleUpdateNotifier();
  }
}
