package musin.seeker.vk.relation;

import musin.seeker.relation.TreeMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public class VkRelationList extends TreeMapRelationList<VkUser, VkRelation, VkUpdate> {

  public VkRelationList(Stream<? extends VkRelation> relations) {
    relations.forEach(this::add);
  }

  public VkRelationList(List<? extends VkUpdate> updates) {
    updates.forEach(u -> add(u.getNow()));
  }

  @Override
  protected VkUpdate createUpdate(@NotNull VkUser target, VkRelation was, VkRelation now) {
    return new VkUpdateImpl(target, was, now);
  }
}
