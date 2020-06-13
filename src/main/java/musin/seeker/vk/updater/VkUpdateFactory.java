package musin.seeker.vk.updater;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.db.model.RelationChange;
import musin.seeker.vk.relation.VkRelation;
import musin.seeker.vk.relation.VkRelationFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VkUpdateFactory {

  private final VkUserFactory vkUserFactory;
  private final VkRelationFactory vkRelationFactory;

  public VkUpdate create(RelationChange change) {
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
      owner = vkUserFactory.create(change.getOwner());
      target = vkUserFactory.create(change.getTarget());
      was = vkRelationFactory.create(target, change.getPrevType());
      now = vkRelationFactory.create(target, change.getCurType());
      time = change.getTime();
    }
  }
}
