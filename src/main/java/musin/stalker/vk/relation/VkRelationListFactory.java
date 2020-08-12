package musin.stalker.vk.relation;

import musin.stalker.relation.list.RelationList;
import musin.stalker.relation.list.RelationListFactory;
import musin.stalker.relation.list.SingleHashMapRelationList;
import org.springframework.stereotype.Component;

@Component
public class VkRelationListFactory implements RelationListFactory {
  @Override
  public RelationList create() {
    return new SingleHashMapRelationList();
  }
}
