package musin.socialstalker.vk.updater;

import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.RelationListPullerBase;
import musin.socialstalker.vk.api.VkApi;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.relation.*;
import org.springframework.stereotype.Component;

import static musin.socialstalker.vk.relation.VkRelationType.FOLLOWER;
import static musin.socialstalker.vk.relation.VkRelationType.FRIEND;

@Component
public class VkRelationListPuller extends RelationListPullerBase<
    VkID,
    VkRelationType
    > {
  public VkRelationListPuller(RelationListFactory<VkRelationList> relationListFactory,
                              VkUpdateFactory updateFactory,
                              VkRelationFactory relationFactory,
                              VkUserFactory userFactory,
                              VkApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::getFriendsAsync, FRIEND);
    registerQuery(api::getFollowersAsync, FOLLOWER);
  }
}
