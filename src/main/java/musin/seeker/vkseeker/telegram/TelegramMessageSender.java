package musin.seeker.vkseeker.telegram;

public interface TelegramMessageSender {
  void sendMessage(String message, boolean waitForExecutionEnd);
}
