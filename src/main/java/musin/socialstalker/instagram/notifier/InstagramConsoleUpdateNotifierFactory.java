package musin.socialstalker.instagram.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramConsoleUpdateNotifierFactory implements UpdateNotifierFactory<InstagramRelationType> {
  @Override
  public UpdateNotifier<InstagramRelationType> create(Stalker stalker) {
    return new ConsoleUpdateNotifier<>();
  }
}
