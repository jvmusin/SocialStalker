package musin.stalker.notifier;

import org.jetbrains.annotations.NotNull;

public interface AsyncMessageSender extends MessageSender {
  default void sendMessage(@NotNull String text, boolean waitUntilNotSent) {
    sendMessage(text);
  }
}
