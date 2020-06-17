package musin.seeker.vk.db;

import musin.seeker.db.seeker.SeekerRepository;
import musin.seeker.updater.SeekerServiceBase;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.api.VkIdFactory;
import musin.seeker.vk.config.VkServiceProperties;
import org.springframework.stereotype.Service;

@Service
public class VkSeekerService extends SeekerServiceBase<VkID> {
  public VkSeekerService(SeekerRepository seekerRepository,
                         VkServiceProperties properties,
                         VkIdFactory idFactory) {
    super(seekerRepository, properties, idFactory);
  }
}
