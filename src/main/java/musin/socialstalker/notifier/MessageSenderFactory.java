package musin.socialstalker.notifier;

import musin.socialstalker.db.model.Stalker;

public interface MessageSenderFactory {
  MessageSender create(Stalker stalker);
}
