package musin.seeker.notifier;

import org.jetbrains.annotations.NotNull;

public interface AsyncMessageSender extends MessageSender {
  default void sendMessage(@NotNull String message, boolean waitUntilNotSent) {
    sendMessage(message);
  }
}
