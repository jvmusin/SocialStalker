package musin.seeker.vkseeker.vk.relation;

import musin.seeker.vkseeker.relation.RelationListBase;

import java.util.stream.Stream;

public class VkRelationList extends RelationListBase<Integer, VkRelation, VkRelationUpdate> {

  public VkRelationList(Stream<VkRelation> relations) {
    relations.forEach(this::add);
  }

  @Override
  protected VkRelationUpdate createUpdate(Integer user, VkRelation was, VkRelation now) {
    return new VkRelationUpdate(user, was, now);
  }
}
