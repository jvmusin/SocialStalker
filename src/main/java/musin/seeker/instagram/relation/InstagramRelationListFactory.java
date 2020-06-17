package musin.seeker.instagram.relation;

import lombok.RequiredArgsConstructor;
import musin.seeker.relation.MultiHashMapRelationList;
import musin.seeker.relation.RelationListFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InstagramRelationListFactory implements RelationListFactory<InstagramRelationList> {

  private final InstagramUpdateFactory updateFactory;
  private final InstagramRelationFactory relationFactory;

  @Override
  public InstagramRelationList create() {
    return new InstagramRelationListImpl(updateFactory, relationFactory);
  }

  private static class InstagramRelationListImpl extends MultiHashMapRelationList<
      InstagramUser,
      InstagramRelationType,
      InstagramRelation,
      InstagramUpdate>
      implements InstagramRelationList {

    public InstagramRelationListImpl(InstagramUpdateFactory updateFactory,
                                     InstagramRelationFactory relationFactory) {
      super(updateFactory, relationFactory);
    }
  }
}
