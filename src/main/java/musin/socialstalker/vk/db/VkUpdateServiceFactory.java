package musin.socialstalker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.db.repository.RelationUpdateRepository;
import musin.socialstalker.updater.UpdateService;
import musin.socialstalker.updater.UpdateServiceBase;
import musin.socialstalker.updater.UpdateServiceFactory;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.config.VkNetworkProperties;
import musin.socialstalker.vk.notifier.VkNotifiableUpdate;
import musin.socialstalker.vk.notifier.VkNotifiableUpdateFactory;
import musin.socialstalker.vk.relation.VkRelationList;
import musin.socialstalker.vk.relation.VkRelationListFactory;
import musin.socialstalker.vk.relation.VkUpdate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VkUpdateServiceFactory
    implements UpdateServiceFactory<VkID, VkUpdate, VkRelationList, VkNotifiableUpdate> {

  private final RelationUpdateRepository relationUpdateRepository;
  private final VkNotifiableUpdateFactory notifiableUpdateFactory;
  private final VkNetworkProperties networkProperties;
  private final VkRelationListFactory relationListFactory;

  @Override
  public UpdateService<VkID, VkUpdate, VkRelationList, VkNotifiableUpdate> create(Stalker stalker) {
    return new UpdateServiceBase<>(
        stalker,
        relationUpdateRepository,
        notifiableUpdateFactory,
        networkProperties,
        relationListFactory
    );
  }
}
