package musin.stalker.instagram.db;

import musin.stalker.db.repository.MonitoringRepository;
import musin.stalker.db.repository.RelationUpdateRepository;
import musin.stalker.instagram.config.InstagramNetworkProperties;
import musin.stalker.instagram.notifier.InstagramNotifiableUpdateFactory;
import musin.stalker.instagram.relation.InstagramRelationListFactory;
import musin.stalker.updater.GeneralUpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramGeneralUpdateService extends GeneralUpdateServiceImpl {
  public InstagramGeneralUpdateService(
      MonitoringRepository monitoringRepository,
      RelationUpdateRepository relationUpdateRepository,
      InstagramNotifiableUpdateFactory notifiableUpdateFactory,
      InstagramNetworkProperties networkProperties,
      InstagramRelationListFactory relationListFactory) {
    super(monitoringRepository, relationUpdateRepository, notifiableUpdateFactory, networkProperties, relationListFactory);
  }
}
