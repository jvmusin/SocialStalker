package musin.socialstalker.updater;

import java.util.List;

public interface MonitoringService<ID> {
  List<ID> findAllTargets();

  void addTarget(ID userId);

  void deleteTarget(ID userId);
}
