package musin.seeker.vk.updater;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.RelationChange;
import musin.seeker.notifier.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VkUpdateFactory {

  private final VkUserFactory userFactory;

  public VkUpdate createUpdate(RelationChange change) {
    return new VkUpdateImpl(change);
  }

  @Data
  private class VkUpdateImpl implements VkUpdate {
    final int id;
    final User owner;
    final User target;
    final VkRelation was;
    final VkRelation now;
    final LocalDateTime time;

    VkUpdateImpl(RelationChange change) {
      id = change.getId();
      owner = userFactory.createUser(change.getOwner());
      target = userFactory.createUser(change.getTarget());
      was = new VkRelation(target, change.getPrevType());
      now = new VkRelation(target, change.getCurType());
      time = change.getTime();
    }
  }
}
