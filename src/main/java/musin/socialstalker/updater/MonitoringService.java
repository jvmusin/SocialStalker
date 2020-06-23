package musin.socialstalker.updater;

import java.util.List;

public interface MonitoringService<ID> {
  List<? extends ID> findAllTargets();

  boolean createMonitoring(ID targetId);

  boolean deleteMonitoring(ID targetId);
}
