package musin.stalker.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.stalker.api.Id;
import musin.stalker.config.NetworkProperties;
import musin.stalker.api.IdFactory;
import musin.stalker.db.model.RelationUpdate;
import musin.stalker.relation.RelationType;
import musin.stalker.relation.RelationTypeFactory;
import musin.stalker.relation.User;
import musin.stalker.relation.UserFactory;

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
