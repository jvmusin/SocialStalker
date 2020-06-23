package musin.socialstalker.instagram.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import org.springframework.stereotype.Component;

@Component
public class InstagramConsoleUpdateNotifierFactory implements InstagramUpdateNotifierFactory {
  @Override
  public UpdateNotifier create(Stalker stalker) {
    return new ConsoleUpdateNotifier();
  }
}
