package musin.stalker.telegram.bot.service;

import musin.stalker.db.model.Stalker;

public interface NetworkFactory {
  Network create(Stalker stalker);
}
