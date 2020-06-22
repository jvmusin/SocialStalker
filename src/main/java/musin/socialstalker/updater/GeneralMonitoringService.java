package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

import java.util.List;

public interface GeneralMonitoringService<ID> {
  List<ID> findAllTargets(Stalker stalker);

  void createMonitoring(Stalker stalker, ID targetId);

  void deleteMonitoring(Stalker stalker, ID targetId);
}
