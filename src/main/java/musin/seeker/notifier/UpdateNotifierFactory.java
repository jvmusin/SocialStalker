package musin.seeker.notifier;

import musin.seeker.db.model.Stalker;

public interface UpdateNotifierFactory<TNotifiableUpdate extends NotifiableUpdate<?, ?>> {
  UpdateNotifier<TNotifiableUpdate> create(Stalker stalker);
}
