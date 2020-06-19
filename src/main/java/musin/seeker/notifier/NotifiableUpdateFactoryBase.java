package musin.seeker.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.config.ServiceProperties;
import musin.seeker.db.IdFactory;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.relation.RelationTypeFactory;
import musin.seeker.relation.UserFactory;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public abstract class NotifiableUpdateFactoryBase<
    ID,
    TUser,
    TRelationType,
    TNotifiableUpdate extends NotifiableUpdate<TUser, TRelationType>>
    implements NotifiableUpdateFactory<TUser, TRelationType, TNotifiableUpdate> {

  private final UserFactory<ID, TUser> userFactory;
  private final RelationTypeFactory<TRelationType> relationTypeFactory;
  private final ServiceProperties serviceProperties;
  private final IdFactory<ID> idFactory;

  @Data
  protected abstract class NotifiableUpdateBase implements NotifiableUpdate<TUser, TRelationType> {
    private final String resource = serviceProperties.getResource();
    private final Integer id;
    private final TUser owner;
    private final TUser target;
    private final TRelationType was;
    private final TRelationType now;
    private final LocalDateTime time;

    protected NotifiableUpdateBase(RelationUpdate update) {
      id = update.getId();
      owner = userFactory.create(idFactory.parse(update.getOwner()));
      target = userFactory.create(idFactory.parse(update.getTarget()));
      was = relationTypeFactory.parseNullSafe(update.getWas());
      now = relationTypeFactory.parseNullSafe(update.getNow());
      time = update.getTime();
    }
  }
}
