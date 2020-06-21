package musin.socialstalker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.MonitoringRepository;
import musin.socialstalker.updater.SeekerService;
import musin.socialstalker.updater.SeekerServiceBase;
import musin.socialstalker.updater.SeekerServiceFactory;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.api.VkIdFactory;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.relation.VkUpdateFactory;
import musin.socialstalker.vk.updater.VkRelationListPuller;
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
