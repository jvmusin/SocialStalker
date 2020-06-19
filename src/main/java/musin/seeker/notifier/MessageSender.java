package musin.seeker.notifier;

import org.jetbrains.annotations.NotNull;

public interface MessageSender {
  void sendMessage(@NotNull String text);
}
