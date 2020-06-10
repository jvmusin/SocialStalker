package musin.seeker.vkseeker.vk.relation;

import musin.seeker.vkseeker.updater.v3.RelationListImpl;

import java.util.stream.Stream;

public class VkRelationList extends RelationListImpl<Integer, VkRelation, VkRelationUpdate> {

  public VkRelationList(Stream<VkRelation> relations) {
    relations.forEach(this::add);
  }

  @Override
  protected VkRelationUpdate createUpdate(Integer user, VkRelation was, VkRelation now) {
    return new VkRelationUpdate(user, was, now);
  }
}
