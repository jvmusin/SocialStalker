package musin.socialstalker.vk.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
public class VkConsoleUpdateNotifierFactory implements UpdateNotifierFactory<VkNotifiableUpdate> {
  @Override
  public UpdateNotifier<VkNotifiableUpdate> create(Stalker stalker) {
    //todo user stalker somehow maybe?
    return new ConsoleUpdateNotifier<>();
  }
}
