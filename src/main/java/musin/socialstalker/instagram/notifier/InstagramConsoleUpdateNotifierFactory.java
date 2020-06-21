package musin.socialstalker.instagram.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramConsoleUpdateNotifierFactory implements UpdateNotifierFactory<InstagramNotifiableUpdate> {
  @Override
  public UpdateNotifier<InstagramNotifiableUpdate> create(Stalker stalker) {
    return new ConsoleUpdateNotifier<>();
  }
}
