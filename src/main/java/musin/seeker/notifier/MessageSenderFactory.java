package musin.seeker.notifier;

import musin.seeker.db.model.Stalker;

public interface MessageSenderFactory {
  MessageSender create(Stalker stalker);
}
