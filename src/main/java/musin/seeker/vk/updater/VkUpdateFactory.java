package musin.seeker.vk.updater;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.RelationChange;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VkUpdateFactory {

  private final VkUserFactory vkUserFactory;

  public VkUpdate createUpdate(RelationChange change) {
    return new VkUpdateImpl(change);
  }

  @Data
  private class VkUpdateImpl implements VkUpdate {
    final int id;
    final VkUser owner;
    final VkUser target;
    final VkRelation was;
    final VkRelation now;
    final LocalDateTime time;

    VkUpdateImpl(RelationChange change) {
      id = change.getId();
      owner = vkUserFactory.createUser(change.getOwner());
      target = vkUserFactory.createUser(change.getTarget());
      was = new VkRelation(target, change.getPrevType());
      now = new VkRelation(target, change.getCurType());
      time = change.getTime();
    }
  }
}
