package musin.seeker.telegram.bot.service;

import musin.seeker.db.model.Stalker;

public interface NetworkFactory {
  Network create(Stalker stalker);
}
