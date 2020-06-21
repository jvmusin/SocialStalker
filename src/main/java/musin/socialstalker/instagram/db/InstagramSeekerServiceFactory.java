package musin.socialstalker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.instagram.api.InstagramID;
import musin.socialstalker.instagram.api.InstagramIdFactory;
import musin.socialstalker.instagram.config.InstagramNetworkProperties;
import musin.socialstalker.instagram.relation.InstagramUpdateFactory;
import musin.socialstalker.instagram.updater.InstagramRelationListPuller;
import musin.socialstalker.updater.SeekerService;
import musin.socialstalker.updater.SeekerServiceBase;
import musin.socialstalker.updater.SeekerServiceFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramSeekerServiceFactory implements SeekerServiceFactory<InstagramID> {

  private final InstagramNetworkProperties properties;
  private final MonitoringRepository monitoringRepository;
  private final InstagramIdFactory idFactory;
  private final InstagramRelationListPuller relationListPuller;
  private final InstagramUpdateServiceFactory updateServiceFactory;
  private final InstagramUpdateFactory updateFactory;

  @Override
  public SeekerService<InstagramID> create(Stalker stalker) {
    return new SeekerServiceBase<>(
        stalker,
        monitoringRepository,
        properties,
        idFactory,
        relationListPuller,
        updateServiceFactory.create(stalker),
        updateFactory
    );
  }
}
