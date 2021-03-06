package musin.socialstalker.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.socialstalker.api.Id;
import musin.socialstalker.config.NetworkProperties;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.RelationTypeFactory;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.UserFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class NotifiableUpdateFactoryImpl<ID extends Id> implements NotifiableUpdateFactory {

  private final UserFactory<ID> userFactory;
  private final RelationTypeFactory relationTypeFactory;
  private final NetworkProperties networkProperties;
  private final IdFactory<ID> idFactory;

  @Override
  public NotifiableUpdate create(RelationUpdate relationUpdate) {
    return new NotifiableUpdateImpl(relationUpdate);
  }

  @Data
  protected class NotifiableUpdateImpl implements NotifiableUpdate {
    private final String network = networkProperties.getNetwork();
    private final Integer id;
    private final User target;
    private final User suspected;
    private final RelationType was;
    private final RelationType now;
    private final LocalDateTime time;

    public NotifiableUpdateImpl(RelationUpdate update) {
      id = update.getId();
      target = userFactory.create(idFactory.parse(update.getTarget()));
      suspected = userFactory.create(idFactory.parse(update.getSuspected()));
      was = relationTypeFactory.parseNullSafe(update.getWas());
      now = relationTypeFactory.parseNullSafe(update.getNow());
      time = update.getTime();
    }
  }
}
