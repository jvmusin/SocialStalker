package musin.socialstalker.instagram.relation;

import musin.socialstalker.relation.list.MultiHashMapRelationList;
import musin.socialstalker.relation.list.RelationList;
import musin.socialstalker.relation.list.RelationListFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationListFactory implements RelationListFactory {

  @Override
  public RelationList create() {
    return new InstagramRelationListImpl();
  }

  private static class InstagramRelationListImpl
      extends MultiHashMapRelationList
      implements InstagramRelationList {
  }
}
