package musin.socialstalker.notifier;

import java.util.List;

public interface UpdateNotifier<TNotifiableUpdate extends NotifiableUpdate<?>> {
  void notify(TNotifiableUpdate update);

  default void notify(List<? extends TNotifiableUpdate> updates) {
    updates.forEach(this::notify);
  }
}
