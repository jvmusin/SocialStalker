package musin.seeker.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.update.RelationUpdate;
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
    implements NotifiableUpdateFactory<ID, TUser, TRelationType, TNotifiableUpdate> {

  private final UserFactory<ID, TUser> userFactory;
  private final RelationTypeFactory<TRelationType> relationTypeFactory;

  @Data
  protected abstract class NotifiableUpdateBase implements NotifiableUpdate<TUser, TRelationType> {
    private final Integer id;
    private final TUser owner;
    private final TUser target;
    private final TRelationType was;
    private final TRelationType now;
    private final LocalDateTime time;

    protected NotifiableUpdateBase(RelationUpdate update) {
      id = update.getId();
      owner = userFactory.create(parseId(update.getOwner()));
      target = userFactory.create(parseId(update.getTarget()));
      was = relationTypeFactory.parseNullSafe(update.getWas());
      now = relationTypeFactory.parseNullSafe(update.getNow());
      time = update.getTime();
    }

    protected abstract ID parseId(String id);
  }
}
