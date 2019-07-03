package musin.seeker.vkseeker.telegram;

import musin.seeker.vkseeker.db.model.RelationChange;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

public interface ChangesSender extends LongPollingBot {
    void sendMessage(RelationChange relationChange);
}
