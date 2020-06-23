package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface UpdateNotifierFactory<TUpdate, TRelationType> {
  UpdateNotifier<TUpdate, TRelationType> create(Stalker stalker);
}
