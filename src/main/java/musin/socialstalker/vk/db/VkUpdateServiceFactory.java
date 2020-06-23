package musin.socialstalker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.updater.UpdateServiceImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkUpdateServiceFactory
    implements UpdateServiceFactory<VkID, VkRelationType> {

  private final VkGeneralUpdateService generalUpdateService;

  @Override
  public UpdateService<VkID, VkRelationType> create(Stalker stalker) {
    return new UpdateServiceImpl<>(stalker, generalUpdateService);
  }
}
