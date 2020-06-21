package musin.seeker.vk.notifier;

import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.ConsoleUpdateNotifier;
import musin.seeker.notifier.UpdateNotifier;
import musin.seeker.notifier.UpdateNotifierFactory;
import org.springframework.stereotype.Component;

@Component
public class VkConsoleUpdateNotifierFactory implements UpdateNotifierFactory<VkNotifiableUpdate> {
  @Override
  public UpdateNotifier<VkNotifiableUpdate> create(Stalker stalker) {
    //todo user stalker somehow maybe?
    return new ConsoleUpdateNotifier<>();
  }
}
