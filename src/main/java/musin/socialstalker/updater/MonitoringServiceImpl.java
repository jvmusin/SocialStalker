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
  public void createMonitoring(ID targetId) {
    generalMonitoringService.createMonitoring(stalker, targetId);
  }

  @Override
  public void deleteMonitoring(ID targetId) {
    generalMonitoringService.deleteMonitoring(stalker, targetId);
  }
}
