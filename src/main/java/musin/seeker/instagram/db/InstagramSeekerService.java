package musin.seeker.instagram.db;

import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.api.InstagramIdFactory;
import musin.seeker.instagram.config.InstagramNetworkProperties;
import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.*;
import musin.seeker.instagram.updater.InstagramRelationListPuller;
import musin.seeker.updater.SeekerServiceBase;
import org.springframework.stereotype.Service;

@Service
public class InstagramSeekerService extends SeekerServiceBase<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramUpdate,
    InstagramRelationList,
    InstagramNotifiableUpdate> {
  public InstagramSeekerService(SeekerRepository seekerRepository,
                                InstagramNetworkProperties properties,
                                InstagramIdFactory idFactory,
                                InstagramRelationListPuller relationListPuller,
                                InstagramUpdateService updateService,
                                InstagramUpdateFactory updateFactory) {
    super(seekerRepository, properties, idFactory, relationListPuller, updateService, updateFactory);
  }
}
