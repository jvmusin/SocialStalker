package musin.seeker.vkseeker.telegram;

import musin.seeker.vkseeker.db.model.RelationChange;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

import java.util.List;

public interface ChangesNotifier {
    void notify(RelationChange relationChange);
    void notify(List<RelationChange> relationChanges);
    void sendMessage(String message);
    void sendMessage(String message, long uid);
}
