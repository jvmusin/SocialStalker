package musin.socialstalker.vk.updater;

import musin.socialstalker.relation.RelationFactory;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.UpdateFactory;
import musin.socialstalker.relation.UserFactory;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.updater.RelationListPullerBase;
import musin.socialstalker.vk.api.VkApi;
import musin.socialstalker.vk.api.VkID;
import musin.socialstalker.vk.relation.*;
import org.springframework.stereotype.Component;

import static musin.socialstalker.vk.relation.VkRelationType.FOLLOWER;
import static musin.socialstalker.vk.relation.VkRelationType.FRIEND;

@Component
public class VkRelationListPuller extends RelationListPullerBase<VkID, RelationType> {
  public VkRelationListPuller(VkRelationListFactory relationListFactory,
                              VkUpdateFactory updateFactory,
                              VkRelationFactory relationFactory,
                              VkUserFactory userFactory,
                              VkApi api) {
    super(relationListFactory, updateFactory, relationFactory, userFactory);
    registerQuery(api::getFriendsAsync, FRIEND);
    registerQuery(api::getFollowersAsync, FOLLOWER);
  }
}
