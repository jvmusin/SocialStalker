package musin.socialstalker.instagram.db;

import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.relation.InstagramRelationList;
import musin.socialstalker.instagram.relation.InstagramRelationType;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.instagram.relation.InstagramUser;
import musin.socialstalker.notifier.NotifiableUpdateFactory;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.GeneralUpdateServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class InstagramGeneralUpdateService extends GeneralUpdateServiceImpl<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramUpdate,
    InstagramRelationList,
    InstagramNotifiableUpdate> {

  public InstagramGeneralUpdateService(RelationUpdateRepository relationUpdateRepository,
                                       NotifiableUpdateFactory<InstagramUser, InstagramRelationType, InstagramNotifiableUpdate> notifiableUpdateFactory,
                                       InstagramNetworkProperties networkProperties,
                                       RelationListFactory<InstagramRelationList> relationListFactory) {
    super(relationUpdateRepository, notifiableUpdateFactory, networkProperties, relationListFactory);
  }
}
