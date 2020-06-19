package musin.seeker.vk.db;

import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.updater.SeekerServiceBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.config.VkServiceProperties;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.relation.*;
import musin.seeker.vk.updater.VkRelationListPuller;
import org.springframework.stereotype.Service;

@Service
public class VkSeekerService extends SeekerServiceBase<
    VkID,
    VkUser,
    VkRelationType,
    VkUpdate,
    VkRelationList,
    VkNotifiableUpdate> {
  public VkSeekerService(SeekerRepository seekerRepository,
                         VkServiceProperties properties,
                         VkIdFactory idFactory,
                         VkRelationListPuller relationListPuller,
                         VkUpdateService updateService,
                         VkUpdateFactory updateFactory) {
    super(seekerRepository, properties, idFactory, relationListPuller, updateService, updateFactory);
  }
}
