package musin.socialstalker.vk.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import musin.socialstalker.vk.relation.VkRelationType;
import org.springframework.stereotype.Component;

@Component
public class VkConsoleUpdateNotifierFactory implements UpdateNotifierFactory<VkNotifiableUpdate, VkRelationType> {
  @Override
  public UpdateNotifier<VkNotifiableUpdate, VkRelationType> create(Stalker stalker) {
    //todo user stalker somehow maybe?
    return new ConsoleUpdateNotifier<>();
  }
}
