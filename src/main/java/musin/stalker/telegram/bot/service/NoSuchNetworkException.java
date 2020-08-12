package musin.stalker.telegram.bot.service;

public class NoSuchNetworkException extends RuntimeException {
  public NoSuchNetworkException(String message) {
    super(message);
  }
}
