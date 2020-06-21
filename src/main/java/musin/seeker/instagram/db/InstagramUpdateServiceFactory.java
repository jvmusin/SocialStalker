package musin.seeker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.db.repository.RelationUpdateRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.config.InstagramNetworkProperties;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdateFactory;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramRelationListFactory;
import musin.seeker.instagram.relation.InstagramUpdate;
import musin.seeker.updater.UpdateService;
import musin.seeker.updater.UpdateServiceBase;
import musin.seeker.updater.UpdateServiceFactory;
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
