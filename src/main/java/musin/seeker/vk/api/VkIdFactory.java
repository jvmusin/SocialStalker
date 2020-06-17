package musin.seeker.vk.api;

import musin.seeker.db.IdFactory;
import org.springframework.stereotype.Component;

@Component
public class VkIdFactory implements IdFactory<VkID> {
  @Override
  public VkID create(String id) {
    return new VkID(id);
  }
}
