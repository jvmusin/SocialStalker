package musin.seeker.vkseeker.telegram.api;

public interface TelegramMessageSender {
  void sendMessage(String message, boolean waitForExecutionEnd);
}
