package musin.seeker.vk.relation;

import musin.seeker.relation.SingleHashMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class VkRelationList extends SingleHashMapRelationList<VkUser, VkRelationType, VkRelation, VkRelationUpdate> {

  public VkRelationList(Stream<? extends VkRelation> relations) {
    relations.forEach(r -> apply(createUpdate(r.getUser(), null, r.getType())));
  }

  public VkRelationList(List<? extends VkRelationUpdate> updates) {
    updates.forEach(this::apply);
  }

  @Override
  protected @NotNull VkRelationUpdate createUpdate(@NotNull VkUser user, VkRelationType was, VkRelationType now) {
    return new VkRelationUpdateImpl(user, was, now);
  }

  @Override
  protected @NotNull VkRelation createRelation(@NotNull VkUser user, VkRelationType type) {
    return new VkRelation(user, type);
  }
}
