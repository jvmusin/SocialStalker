package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;

public interface MonitoringServiceFactory<ID> {
  MonitoringService<ID> create(Stalker stalker);
}
