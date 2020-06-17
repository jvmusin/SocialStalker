package musin.seeker.instagram.notifier;

import musin.seeker.db.update.RelationUpdate;
import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.db.InstagramConstants;
import musin.seeker.instagram.relation.InstagramRelationType;
import musin.seeker.instagram.relation.InstagramRelationTypeFactory;
import musin.seeker.instagram.relation.InstagramUser;
import musin.seeker.instagram.relation.InstagramUserFactory;
import musin.seeker.notifier.NotifiableUpdateFactoryBase;
import org.springframework.stereotype.Component;

@Component
public class InstagramNotifiableUpdateFactory
    extends NotifiableUpdateFactoryBase<InstagramID, InstagramUser, InstagramRelationType, InstagramNotifiableUpdate> {
  public InstagramNotifiableUpdateFactory(InstagramUserFactory userFactory, InstagramRelationTypeFactory relationTypeFactory) {
    super(userFactory, relationTypeFactory);
  }

  @Override
  public InstagramNotifiableUpdate create(RelationUpdate update) {
    return new InstagramNotifiableUpdateImpl(update);
  }

  private class InstagramNotifiableUpdateImpl extends NotifiableUpdateBase implements InstagramNotifiableUpdate {
    protected InstagramNotifiableUpdateImpl(RelationUpdate update) {
      super(update);
    }

    @Override
    protected InstagramID parseId(String id) {
      return new InstagramID(id);
    }

    @Override
    public String getResource() {
      return InstagramConstants.RESOURCE;
    }
  }
}
