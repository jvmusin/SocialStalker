package musin.stalker.updater;

import musin.stalker.db.Id;
import musin.stalker.db.model.Stalker;

import java.util.List;

public interface GeneralMonitoringService<ID extends Id> {
  List<ID> findAllTargets(Stalker stalker);

  boolean exists(Stalker stalker, ID targetId);

  boolean createMonitoring(Stalker stalker, ID targetId);

  boolean deleteMonitoring(Stalker stalker, ID targetId);
}
