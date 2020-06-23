package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface UpdateNotifierFactory<TRelationType> {
  UpdateNotifier<? super TRelationType> create(Stalker stalker);
}
