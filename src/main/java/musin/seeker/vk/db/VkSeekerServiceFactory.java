package musin.seeker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.db.repository.MonitoringRepository;
import musin.seeker.updater.SeekerService;
import musin.seeker.updater.SeekerServiceBase;
import musin.seeker.updater.SeekerServiceFactory;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.config.VkNetworkProperties;
import musin.seeker.vk.relation.VkUpdateFactory;
import musin.seeker.vk.updater.VkRelationListPuller;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkSeekerServiceFactory implements SeekerServiceFactory<VkID> {

  private final MonitoringRepository monitoringRepository;
  private final VkNetworkProperties properties;
  private final VkIdFactory idFactory;
  private final VkRelationListPuller relationListPuller;
  private final VkUpdateServiceFactory updateServiceFactory;
  private final VkUpdateFactory updateFactory;

  @Override
  public SeekerService<VkID> create(Stalker stalker) {
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
