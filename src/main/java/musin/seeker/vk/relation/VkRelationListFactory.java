package musin.seeker.vk.relation;

import musin.seeker.relation.list.RelationListFactory;
import musin.seeker.relation.list.SingleHashMapRelationList;
import org.springframework.stereotype.Component;

@Component
public class VkRelationListFactory implements RelationListFactory<VkRelationList> {

  @Override
  public VkRelationList create() {
    return new VkRelationListImpl();
  }

  private static class VkRelationListImpl
      extends SingleHashMapRelationList<VkUser, VkRelationType>
      implements VkRelationList {
  }
}
