package musin.seeker.vkseeker.vk.relation;

import musin.seeker.vkseeker.relation.TreeMapRelationList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class VkRelationList extends TreeMapRelationList<Integer, VkRelation, VkRelationUpdate> {

  public VkRelationList(Stream<VkRelation> relations) {
    relations.forEach(this::add);
  }

  @Override
  protected VkRelationUpdate createUpdate(@NotNull Integer user, VkRelation was, VkRelation now) {
    return new VkRelationUpdate(user, was, now);
  }
}
