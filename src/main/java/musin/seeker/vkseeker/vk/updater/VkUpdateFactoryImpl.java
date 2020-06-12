package musin.seeker.vkseeker.vk.updater;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import musin.seeker.vkseeker.db.model.RelationChange;
import musin.seeker.vkseeker.notifier.User;
import musin.seeker.vkseeker.vk.SimpleVkUser;
import musin.seeker.vkseeker.vk.VkApi;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class VkUpdateFactoryImpl implements VkUpdateFactory {

  private final VkApi vkApi;

  @Override
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
      owner = new VkUser(change.getOwner());
      target = new VkUser(change.getTarget());
      was = new VkRelation(target, change.getPrevType());
      now = new VkRelation(target, change.getCurType());
      time = change.getTime();
    }
  }

  @Data
  private class VkUser implements User {
    private final int id;

    @Override
    public String getName() {
      SimpleVkUser user = vkApi.loadUser(id);
      return String.format("%d: %s %s", id, user.getFirstName(), user.getLastName());
    }

    @Override
    public String getLink() {
      return "https://vk.com/id" + id;
    }
  }
}
