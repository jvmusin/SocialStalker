package musin.socialstalker.instagram.relation;

import musin.socialstalker.relation.list.MultiHashMapRelationList;
import musin.socialstalker.relation.list.RelationListFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationListFactory implements RelationListFactory<InstagramRelationList> {

  @Override
  public InstagramRelationList create() {
    return new InstagramRelationListImpl();
  }

  private static class InstagramRelationListImpl
      extends MultiHashMapRelationList<InstagramUser, InstagramRelationType>
      implements InstagramRelationList {
  }
}
