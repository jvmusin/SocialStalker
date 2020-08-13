package musin.stalker.vk.api;

import musin.stalker.api.IdFactory;
import org.springframework.stereotype.Component;

@Component
public class VkIdFactory implements IdFactory<VkID> {
  @Override
  public VkID parse(String id) {
    return new VkID(id);
  }
}
