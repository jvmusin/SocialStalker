package musin.stalker.instagram.relation;

import musin.stalker.relation.list.MultiHashMapRelationList;
import musin.stalker.relation.list.RelationList;
import musin.stalker.relation.list.RelationListFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationListFactory implements RelationListFactory {
  @Override
  public RelationList create() {
    return new MultiHashMapRelationList();
  }
}
