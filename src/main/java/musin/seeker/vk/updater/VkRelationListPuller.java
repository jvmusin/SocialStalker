package musin.seeker.vk.updater;

import musin.seeker.relation.RelationListFactory;
import musin.seeker.updater.RelationListPullerBase;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.relation.*;
import org.springframework.stereotype.Component;

import static musin.seeker.vk.relation.VkRelationType.FOLLOWER;
import static musin.seeker.vk.relation.VkRelationType.FRIEND;

@Component
public class VkRelationListPuller extends RelationListPullerBase<
    VkID,
    VkUser,
    VkRelationType,
    VkRelation,
    VkUpdate,
    VkRelationList> {
  public VkRelationListPuller(RelationListFactory<VkRelationList> relationListFactory,
                              VkUpdateFactory updateFactory,
                              VkRelationFactory relationFactory,
                              VkUserFactory userFactory,
                              VkApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::loadFriendsAsync, FRIEND);
    registerQuery(api::loadFollowersAsync, FOLLOWER);
  }
}
