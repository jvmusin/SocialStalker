package musin.socialstalker.updater;

import musin.socialstalker.api.Id;

import java.util.List;

public interface MonitoringService<ID extends Id> {
  List<ID> findAllTargets();

  boolean createMonitoring(ID targetId);

  boolean deleteMonitoring(ID targetId);
}
