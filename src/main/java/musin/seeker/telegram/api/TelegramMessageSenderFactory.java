package musin.seeker.telegram.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import musin.seeker.db.model.Stalker;
import musin.seeker.notifier.MessageSender;
import musin.seeker.notifier.MessageSenderFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.util.concurrent.locks.ReentrantLock;

@Component
@RequiredArgsConstructor
public class TelegramMessageSenderFactory implements MessageSenderFactory {
  private final AbsSender sender;

  @Override
  public MessageSender create(Stalker stalker) {
    return new TelegramMessageSenderImpl(stalker.getTelegramId(), sender);
  }

  @Data
  @Log4j2
  private static class TelegramMessageSenderImpl implements TelegramMessageSender {

    private final long receiverUid;
    private final AbsSender telegramAbsSender;

    @Override
    @SneakyThrows
    public void sendMessage(@NotNull String text, boolean waitUntilNotSent) {
      SyncSentMessageCallback callback = new SyncSentMessageCallback();
      telegramAbsSender.executeAsync(new MarkdownSendMessage(receiverUid, text), callback);
      if (waitUntilNotSent) callback.waitForSending();
    }

    @Override
    public void sendMessage(@NotNull String text) {
      sendMessage(text, false);
    }

    private static class SyncSentMessageCallback implements SentCallback<Message> {
      final ReentrantLock lock = new ReentrantLock();

      @Override
      public void onResult(BotApiMethod<Message> method, Message response) {
        lock.unlock();
      }

      @Override
      public void onError(BotApiMethod<Message> method, TelegramApiRequestException apiException) {
        log.catching(apiException);
        lock.unlock();
      }

      @Override
      public void onException(BotApiMethod<Message> method, Exception exception) {
        log.catching(exception);
        lock.unlock();
      }

      void waitForSending() {
        lock.lock();
      }
    }
  }
}
