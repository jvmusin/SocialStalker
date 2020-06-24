package musin.socialstalker.vk.db;

import musin.socialstalker.updater.UpdateServiceFactoryImpl;
import org.springframework.stereotype.Component;

@Component
public class VkUpdateServiceFactory extends UpdateServiceFactoryImpl {
  public VkUpdateServiceFactory(VkGeneralUpdateService generalUpdateService) {
    super(generalUpdateService);
  }
}
