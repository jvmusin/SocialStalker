package musin.socialstalker.instagram.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.relation.RelationType;
import org.springframework.stereotype.Component;

@Component
public class InstagramConsoleUpdateNotifierFactory implements InstagramUpdateNotifierFactory {
  @Override
  public UpdateNotifier<RelationType> create(Stalker stalker) {
    return new ConsoleUpdateNotifier<>();
  }
}
