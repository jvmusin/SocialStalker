package musin.socialstalker.vk.notifier;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.ConsoleUpdateNotifier;
import musin.socialstalker.notifier.UpdateNotifier;
import org.springframework.stereotype.Component;

@Component
public class VkConsoleUpdateNotifierFactory implements VkUpdateNotifierFactory {
  @Override
  public UpdateNotifier create(Stalker stalker) {
    //todo user stalker somehow maybe?
    return new ConsoleUpdateNotifier();
  }
}
