package musin.stalker.updater;

import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.db.model.Stalker;

@RequiredArgsConstructor
public class MonitoringServiceFactoryImpl<ID extends Id> implements MonitoringServiceFactory<ID> {
  private final GeneralMonitoringService<ID> generalMonitoringService;

  @Override
  public MonitoringService<ID> create(Stalker stalker) {
    return new MonitoringServiceImpl<>(stalker, generalMonitoringService);
  }
}
