package musin.stalker.vk.db;

import musin.stalker.updater.UpdateServiceFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateServiceFactory extends UpdateServiceFactoryImpl {
  public VkUpdateServiceFactory(VkGeneralUpdateService generalUpdateService) {
    super(generalUpdateService);
  }
}
