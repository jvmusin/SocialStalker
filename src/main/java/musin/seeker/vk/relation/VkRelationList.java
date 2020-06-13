package musin.seeker.vk.relation;

import musin.seeker.relation.TreeMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class VkRelationList extends TreeMapRelationList<VkUser, VkRelation, VkUpdate> {

  public VkRelationList(Stream<VkRelation> relations) {
    relations.forEach(this::add);
  }

  @Override
  protected VkUpdate createUpdate(@NotNull VkUser target, VkRelation was, VkRelation now) {
    return new VkUpdate(target, was, now);
  }
}
