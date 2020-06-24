package musin.socialstalker.vk.relation;

import musin.socialstalker.relation.list.RelationList;
import musin.socialstalker.relation.list.RelationListFactory;
import musin.socialstalker.relation.list.SingleHashMapRelationList;
import org.springframework.stereotype.Component;

@Component
public class VkRelationListFactory implements RelationListFactory {
  @Override
  public RelationList create() {
    return new SingleHashMapRelationList();
  }
}
