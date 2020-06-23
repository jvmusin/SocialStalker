package musin.socialstalker.notifier;

import java.util.List;

public interface UpdateNotifier<TRelationType> {
  void notify(NotifiableUpdate<? extends TRelationType> update);

  default void notify(List<? extends NotifiableUpdate<? extends TRelationType>> updates) {
    updates.forEach(this::notify);
  }
}
