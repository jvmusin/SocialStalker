package musin.seeker.telegram.api;

public interface TelegramMessageSender {
  void sendMessage(String message, boolean waitForExecutionEnd);
}
