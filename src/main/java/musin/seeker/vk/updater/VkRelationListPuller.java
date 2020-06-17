package musin.seeker.vk.updater;

import musin.seeker.relation.RelationListFactory;
import musin.seeker.updater.RelationListPullerBase;
import musin.seeker.vk.api.VkApi;
import musin.seeker.vk.api.VkID;
import musin.seeker.vk.relation.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

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

  private final VkApi api;

  public VkRelationListPuller(RelationListFactory<VkRelationList> relationListFactory,
                              VkUpdateFactory updateFactory,
                              VkApi api,
                              VkRelationFactory relationFactory) {
    super(relationListFactory, updateFactory, relationFactory);
    this.api = api;
  }

  @Override
  public CompletableFuture<VkRelationList> pull(VkID userId) {
    return combine(
        load(() -> api.loadFriendsAsync(userId), FRIEND),
        load(() -> api.loadFollowersAsync(userId), FOLLOWER)
    );
  }
}
