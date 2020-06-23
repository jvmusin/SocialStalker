package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

import java.util.List;

public interface GeneralMonitoringService<ID> {
  List<? extends ID> findAllTargets(Stalker stalker);

  boolean exists(Stalker stalker, ID targetId);

  boolean createMonitoring(Stalker stalker, ID targetId);

  boolean deleteMonitoring(Stalker stalker, ID targetId);
}
