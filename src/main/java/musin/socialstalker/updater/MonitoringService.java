package musin.socialstalker.updater;

import java.util.List;

public interface MonitoringService<ID> {
  List<ID> findAllTargets();

  void createMonitoring(ID targetId);

  void deleteMonitoring(ID targetId);
}
