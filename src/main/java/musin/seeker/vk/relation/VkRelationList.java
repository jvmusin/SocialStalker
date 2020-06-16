package musin.seeker.vk.relation;

import musin.seeker.relation.SingleHashMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class VkRelationList extends SingleHashMapRelationList<VkUser, VkRelationType, VkRelation, VkUpdate> {

  public VkRelationList(Stream<? extends VkRelation> relations) {
    initRelations(relations);
  }

  public VkRelationList(List<? extends VkUpdate> updates) {
    initUpdates(updates);
  }

  @Override
  protected @NotNull VkUpdate createUpdate(@NotNull VkUser user, VkRelationType was, VkRelationType now) {
    return new VkUpdateImpl(user, was, now);
  }

  @Override
  protected @NotNull VkRelation createRelation(@NotNull VkUser user, VkRelationType type) {
    return new VkRelation(user, type);
  }
}
