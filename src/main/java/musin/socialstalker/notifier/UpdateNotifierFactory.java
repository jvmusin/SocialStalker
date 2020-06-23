package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface UpdateNotifierFactory<TRelationType> {
  UpdateNotifier<TRelationType> create(Stalker stalker);
}
