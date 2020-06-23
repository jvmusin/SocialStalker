package musin.socialstalker.vk.relation;

import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.relation.list.SingleHashMapRelationList;
import org.springframework.stereotype.Component;

@Component
public class VkRelationListFactory implements RelationListFactory<RelationType> {

  @Override
  public VkRelationList create() {
    return new VkRelationListImpl();
  }

  private static class VkRelationListImpl
      extends SingleHashMapRelationList<RelationType>
      implements VkRelationList {
  }
}
