package musin.stalker.notifier;

import musin.stalker.db.model.Stalker;

public interface UpdateNotifierFactory {
  UpdateNotifier create(Stalker stalker);
}
