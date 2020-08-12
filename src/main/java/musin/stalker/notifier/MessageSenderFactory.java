package musin.stalker.notifier;

import musin.stalker.db.model.Stalker;

public interface MessageSenderFactory {
  MessageSender create(Stalker stalker);
}
