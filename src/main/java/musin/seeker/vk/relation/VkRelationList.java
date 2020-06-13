package musin.seeker.vk.relation;

import musin.seeker.relation.TreeMapRelationList;
import musin.seeker.vk.updater.VkUser;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class VkRelationList extends TreeMapRelationList<VkUser, VkRelation, VkRelationUpdate> {

  public VkRelationList(Stream<VkRelation> relations) {
    relations.forEach(this::add);
  }

  @Override
  protected VkRelationUpdate createUpdate(@NotNull VkUser user, VkRelation was, VkRelation now) {
    return new VkRelationUpdate(user, was, now);
  }
}
