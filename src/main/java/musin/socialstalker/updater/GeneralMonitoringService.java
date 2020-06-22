package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

import java.util.List;

public interface GeneralMonitoringService<ID> {
  List<ID> findAllTargets(Stalker stalker);

  void addTarget(Stalker stalker, ID userId);

  void deleteTarget(Stalker stalker, ID userId);
}
