package musin.socialstalker.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.socialstalker.config.NetworkProperties;
import musin.socialstalker.db.IdFactory;
import musin.socialstalker.db.model.RelationUpdate;
import musin.socialstalker.relation.RelationTypeFactory;
import musin.socialstalker.relation.User;
import musin.socialstalker.relation.UserFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class NotifiableUpdateFactoryBase<ID, TRelationType>
    implements NotifiableUpdateFactory<TRelationType> {

  private final UserFactory<ID> userFactory;
  private final RelationTypeFactory<TRelationType> relationTypeFactory;
  private final NetworkProperties networkProperties;
  private final IdFactory<ID> idFactory;

  @Data
  protected abstract class NotifiableUpdateBase implements NotifiableUpdate<TRelationType> {
    private final String network = networkProperties.getNetwork();
    private final Integer id;
    private final User<?> target;
    private final User<?> suspected;
    private final TRelationType was;
    private final TRelationType now;
    private final LocalDateTime time;

    protected NotifiableUpdateBase(RelationUpdate update) {
      id = update.getId();
      target = userFactory.create(idFactory.parse(update.getTarget()));
      suspected = userFactory.create(idFactory.parse(update.getSuspected()));
      was = relationTypeFactory.parseNullSafe(update.getWas());
      now = relationTypeFactory.parseNullSafe(update.getNow());
      time = update.getTime();
    }
  }
}
