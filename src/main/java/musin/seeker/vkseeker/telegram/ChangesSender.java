package musin.seeker.vkseeker.telegram;

import musin.seeker.vkseeker.db.model.RelationChange;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;

public interface ChangesSender extends WebhookBot {
    void sendMessage(RelationChange relationChange);
}
