package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface UpdateNotifierFactory<TUpdate> {
  UpdateNotifier<TUpdate> create(Stalker stalker);
}
