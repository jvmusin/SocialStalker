package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdate;
import musin.socialstalker.instagram.notifier.InstagramNotifiableUpdateFactory;
import musin.socialstalker.instagram.relation.InstagramRelationList;
import musin.socialstalker.instagram.relation.InstagramRelationListFactory;
import musin.socialstalker.instagram.relation.InstagramUpdate;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceBase;
import musin.socialstalker.updater.UpdateServiceFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramUpdateServiceFactory
    implements UpdateServiceFactory<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> {

  private final RelationUpdateRepository relationUpdateRepository;
  private final InstagramNotifiableUpdateFactory notifiableUpdateFactory;
  private final InstagramNetworkProperties networkProperties;
  private final InstagramRelationListFactory relationListFactory;

  @Override
  public UpdateService<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> create(Stalker stalker) {
    return new UpdateServiceBase<>(
        stalker,
        relationUpdateRepository,
        notifiableUpdateFactory,
        networkProperties,
        relationListFactory
    );
  }
}
