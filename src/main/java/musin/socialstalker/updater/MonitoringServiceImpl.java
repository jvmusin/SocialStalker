package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;

import java.util.List;

@RequiredArgsConstructor
public class MonitoringServiceImpl<ID> implements MonitoringService<ID> {

  private final Stalker stalker;
  private final GeneralMonitoringService<ID> generalMonitoringService;

  @Override
  public List<ID> findAllTargets() {
    return generalMonitoringService.findAllTargets(stalker);
  }

  @Override
  public void addTarget(ID userId) {
    generalMonitoringService.addTarget(stalker, userId);
  }

  @Override
  public void deleteTarget(ID userId) {
    generalMonitoringService.deleteTarget(stalker, userId);
  }
}
