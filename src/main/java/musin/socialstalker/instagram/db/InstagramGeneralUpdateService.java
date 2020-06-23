package musin.socialstalker.instagram.db;

import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.list.RelationList;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramGeneralUpdateService extends GeneralUpdateServiceImpl<InstagramID, InstagramRelationType> {

  public InstagramGeneralUpdateService(
      MonitoringRepository monitoringRepository,
      RelationUpdateRepository relationUpdateRepository,
      NotifiableUpdateFactory<InstagramRelationType> notifiableUpdateFactory,
      InstagramNetworkProperties networkProperties,
      RelationListFactory<InstagramRelationType> relationListFactory) {
    super(monitoringRepository, relationUpdateRepository, notifiableUpdateFactory, networkProperties, relationListFactory);
  }
}
