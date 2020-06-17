package musin.seeker.instagram.relation;

import musin.seeker.instagram.api.InstagramID;
import musin.seeker.relation.RelationFactoryBase;
import musin.seeker.relation.UserFactory;
import org.springframework.stereotype.Component;

@Component
public class InstagramRelationFactory
    extends RelationFactoryBase<InstagramID, InstagramUser, InstagramRelationType, InstagramRelation> {
  public InstagramRelationFactory(UserFactory<InstagramID, InstagramUser> instagramIDInstagramUserUserFactory) {
    super(instagramIDInstagramUserUserFactory);
  }

  @Override
  public InstagramRelation create(InstagramID id, InstagramRelationType type) {
    return new InstagramRelation(createUser(id), type);
  }
}
