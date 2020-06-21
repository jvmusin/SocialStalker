package musin.seeker.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.config.NetworkProperties;
import musin.seeker.db.IdFactory;
import musin.seeker.db.model.RelationUpdate;
import musin.seeker.relation.RelationTypeFactory;
import musin.seeker.relation.User;
import musin.seeker.relation.UserFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class NotifiableUpdateFactoryBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements NotifiableUpdateFactory<TUser, TRelationType, TNotifiableUpdate> {

  private final UserFactory<ID, TUser> userFactory;
  private final RelationTypeFactory<TRelationType> relationTypeFactory;
  private final NetworkProperties networkProperties;
  private final IdFactory<ID> idFactory;

  @Data
  protected abstract class NotifiableUpdateBase implements NotifiableUpdate<TUser, TRelationType> {
    private final String network = networkProperties.getNetwork();
    private final Integer id;
    private final TUser target;
    private final TUser suspected;
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
