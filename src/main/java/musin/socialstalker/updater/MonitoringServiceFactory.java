package musin.socialstalker.updater;

import musin.socialstalker.api.Id;
import musin.socialstalker.db.model.Stalker;

public interface MonitoringServiceFactory<ID extends Id> {
  MonitoringService<ID> create(Stalker stalker);
}
