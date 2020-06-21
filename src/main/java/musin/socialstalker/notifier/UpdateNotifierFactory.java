package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface UpdateNotifierFactory<TNotifiableUpdate extends NotifiableUpdate<?, ?>> {
  UpdateNotifier<TNotifiableUpdate> create(Stalker stalker);
}
