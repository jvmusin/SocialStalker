package musin.seeker.vk.notifier;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.RelationChange;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkRelationFactory;
import musin.seeker.vk.relation.VkUser;
import musin.seeker.vk.relation.VkUserFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VkNotifiableUpdateFactory {
  private final VkUserFactory vkUserFactory;
  private final VkRelationFactory vkRelationFactory;

  public VkNotifiableUpdate create(RelationChange change) {
    return new VkNotifiableUpdateImpl(change);
  }

  @Data
  private class VkNotifiableUpdateImpl implements VkNotifiableUpdate {
    final int id;
    final VkUser owner;
    final VkUser target;
    final VkRelation was;
    final VkRelation now;
    final LocalDateTime time;

    VkNotifiableUpdateImpl(RelationChange change) {
      id = change.getId();
      owner = vkUserFactory.create(change.getOwner());
      target = vkUserFactory.create(change.getTarget());
      was = vkRelationFactory.create(target, change.getPrevType());
      now = vkRelationFactory.create(target, change.getCurType());
      time = change.getTime();
    }
  }
}
