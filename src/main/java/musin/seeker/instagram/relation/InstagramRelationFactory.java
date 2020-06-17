package musin.seeker.instagram.relation;

import musin.seeker.instagram.api.InstagramID;
import musin.seeker.relation.RelationFactoryBase;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory extends RelationFactoryBase<
    InstagramID,
    InstagramUser,
    InstagramRelationType,
    InstagramRelation> {

  public InstagramRelationFactory(InstagramUserFactory userFactory) {
    super(userFactory);
  }

  @Override
  public InstagramRelation createByUser(InstagramUser user, InstagramRelationType type) {
    return new InstagramRelation(user, type);
  }
}
