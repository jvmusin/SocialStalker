package musin.seeker.instagram.notifier;

import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.ConsoleUpdateNotifier;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.notifier.UpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramConsoleUpdateNotifierFactory implements UpdateNotifierFactory<InstagramNotifiableUpdate> {
  @Override
  public UpdateNotifier<InstagramNotifiableUpdate> create(Stalker stalker) {
    return new ConsoleUpdateNotifier<>();
  }
}
