package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface UpdateNotifierFactory {
  UpdateNotifier create(Stalker stalker);
}
