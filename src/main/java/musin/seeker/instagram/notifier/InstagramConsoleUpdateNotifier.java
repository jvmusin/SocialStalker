package musin.seeker.instagram.notifier;

import musin.seeker.notifier.ConsoleUpdateNotifier;
import org.springframework.stereotype.Component;

@Component
public class InstagramConsoleUpdateNotifier extends ConsoleUpdateNotifier<InstagramNotifiableUpdate> implements InstagramUpdateNotifier {
  @Override
  protected int getMinSizeForABunchOfChanges() {
    return Integer.MAX_VALUE;
  }
}
