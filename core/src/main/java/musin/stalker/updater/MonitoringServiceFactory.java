package musin.stalker.updater;

import musin.stalker.db.Id;
import musin.stalker.db.model.Stalker;

public interface MonitoringServiceFactory<ID extends Id> {
  MonitoringService<ID> create(Stalker stalker);
}
