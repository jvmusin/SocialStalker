package musin.seeker.vkseeker.notifiers;

import musin.seeker.vkseeker.db.model.RelationChange;

import java.util.List;

public interface ChangesNotifier {
  void notify(RelationChange change);

  void notify(List<RelationChange> changes);
}
