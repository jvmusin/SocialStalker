package musin.socialstalker.instagram.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdateFactory;
import musin.socialstalker.instagram.relation.InstagramRelationListFactory;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
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
