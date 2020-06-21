package musin.socialstalker.telegram.bot.service;

import musin.socialstalker.db.model.Stalker;

public interface NetworkFactory {
  Network create(Stalker stalker);
}
