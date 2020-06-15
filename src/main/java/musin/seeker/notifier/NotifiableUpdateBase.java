package musin.seeker.notifier;

import lombok.Data;
import musin.seeker.db.update.RelationUpdate;
import musin.seeker.relation.User;

import java.time.LocalDateTime;

@Data
public abstract class NotifiableUpdateBase<ID, TUser extends User<?>, TRelationType> implements NotifiableUpdate<TUser, TRelationType> {
  private final Integer id;
  private final TUser owner;
  private final TUser target;
  private final TRelationType was;
  private final TRelationType now;
  private final LocalDateTime time;

  protected NotifiableUpdateBase(RelationUpdate update) {
    this.id = update.getId();
    owner = createUser(parseId(update.getOwner()));
    target = createUser(parseId(update.getTarget()));
    was = parseRelationType(update.getWas());
    now = parseRelationType(update.getNow());
    time = update.getTime();
  }

  protected abstract TUser createUser(ID id);

  protected abstract ID parseId(String id);

  protected abstract TRelationType parseRelationType(String type);
}
