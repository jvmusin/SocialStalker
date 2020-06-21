package musin.seeker.instagram.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.db.repository.MonitoringRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.api.InstagramIdFactory;
import musin.seeker.instagram.config.InstagramNetworkProperties;
import musin.seeker.instagram.relation.InstagramUpdateFactory;
import musin.seeker.instagram.updater.InstagramRelationListPuller;
import musin.seeker.updater.SeekerService;
import musin.seeker.updater.SeekerServiceBase;
import musin.seeker.updater.SeekerServiceFactory;
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
