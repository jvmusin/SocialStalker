package musin.socialstalker.notifier;

import java.util.List;

public interface UpdateNotifier {
  void notify(NotifiableUpdate update);

  default void notify(List<NotifiableUpdate> updates) {
    updates.forEach(this::notify);
  }
}
