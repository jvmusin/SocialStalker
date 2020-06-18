package musin.seeker.instagram.relation;

import musin.seeker.relation.MultiHashMapRelationList;
import musin.seeker.relation.RelationListFactory;
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
