package musin.stalker.updater;

import musin.stalker.db.Id;

import java.util.List;

public interface MonitoringService<ID extends Id> {
  List<ID> findAllTargets();

  boolean createMonitoring(ID targetId);

  boolean deleteMonitoring(ID targetId);
}
