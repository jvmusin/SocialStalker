package musin.seeker.vk.relation;

import musin.seeker.relation.SingleHashMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class VkRelationList extends SingleHashMapRelationList<VkUser, VkRelation, VkUpdate> {

  public VkRelationList(Stream<? extends VkRelation> relations) {
    relations.forEach(r -> apply(createUpdate(r.getUser(), null, r)));
  }

  public VkRelationList(List<? extends VkUpdate> updates) {
    updates.forEach(this::apply);
  }

  @Override
  protected @NotNull VkUpdate createUpdate(@NotNull VkUser target, VkRelation was, VkRelation now) {
    return new VkUpdateImpl(target, was, now);
  }
}
