package musin.socialstalker.telegram.api;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.util.concurrent.CountDownLatch;

@Slf4j
@RequiredArgsConstructor
class TelegramMessageSenderImpl implements TelegramMessageSender {

  private final long receiverUid;
  private final AbsSender telegramAbsSender;

  @Override
  @SneakyThrows
  public void sendMessage(@NotNull String text, boolean waitUntilSent) {
    SyncSentMessageCallback callback = new SyncSentMessageCallback();
    telegramAbsSender.executeAsync(new MarkdownSendMessage(receiverUid, text), callback);
    if (waitUntilSent) callback.waitForSending();
  }

  @Override
  public void sendMessage(@NotNull String text) {
    sendMessage(text, false);
  }

  private static class SyncSentMessageCallback implements SentCallback<Message> {
    final CountDownLatch latch = new CountDownLatch(1);

    @Override
    public void onResult(BotApiMethod<Message> method, Message response) {
      latch.countDown();
    }

    @Override
    public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {
      log.warn("Error occurred while sending a message " + method, apiException);
      latch.countDown();
    }

    @Override
    public void onException(BotApiMethod<Message> method, Exception exception) {
      log.warn("Exception occurred while sending a message " + method, exception);
      latch.countDown();
    }

    @SneakyThrows
    void waitForSending() {
      latch.await();
    }
  }
}
