package musin.socialstalker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.updater.UpdateServiceImpl;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.notifier.VkNotifiableUpdate;
import musin.socialstalker.vk.relation.VkRelationList;
import musin.socialstalker.vk.relation.VkUpdate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkUpdateServiceFactory
    implements UpdateServiceFactory<VkID, VkUpdate, VkRelationList, VkNotifiableUpdate> {

  private final VkGeneralUpdateService generalUpdateService;

  @Override
  public UpdateService<VkID, VkUpdate, VkRelationList, VkNotifiableUpdate> create(Stalker stalker) {
    return new UpdateServiceImpl<>(stalker, generalUpdateService);
  }
}
