package musin.socialstalker.notifier;

import java.util.List;

public interface UpdateNotifier<TUpdate, TRelationType> {
  void notify(NotifiableUpdate<TRelationType> update);

  default void notify(List<? extends NotifiableUpdate<TRelationType>> updates) {
    updates.forEach(this::notify);
  }
}
