package musin.stalker.vk.updater;

import musin.stalker.relation.RelationFactory;
import musin.stalker.relation.UpdateFactory;
import musin.stalker.vk.api.VkApi;
import musin.stalker.vk.api.VkID;
import musin.stalker.updater.RelationListPullerBase;
import musin.stalker.vk.relation.VkRelationListFactory;
import musin.stalker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

import static musin.stalker.vk.relation.VkRelationType.FOLLOWER;
import static musin.stalker.vk.relation.VkRelationType.FRIEND;

@Component
public class VkRelationListPuller extends RelationListPullerBase<VkID> {
  public VkRelationListPuller(VkRelationListFactory relationListFactory,
                              UpdateFactory updateFactory,
                              RelationFactory relationFactory,
                              VkUserFactory userFactory,
                              VkApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::getFriendsAsync, FRIEND);
    registerQuery(api::getFollowersAsync, FOLLOWER);
  }
}
