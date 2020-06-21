package musin.seeker.vk.db;

import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.Stalker;
import musin.seeker.db.repository.RelationUpdateRepository;
import musin.seeker.updater.UpdateService;
import musin.seeker.updater.UpdateServiceBase;
import musin.seeker.updater.UpdateServiceFactory;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.config.VkNetworkProperties;
import musin.seeker.vk.notifier.VkNotifiableUpdate;
import musin.seeker.vk.notifier.VkNotifiableUpdateFactory;
import musin.seeker.vk.relation.VkRelationList;
import musin.seeker.vk.relation.VkRelationListFactory;
import musin.seeker.vk.relation.VkUpdate;
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
