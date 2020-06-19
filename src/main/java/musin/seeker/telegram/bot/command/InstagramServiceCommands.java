package musin.seeker.telegram.bot.command;

import musin.seeker.instagram.api.InstagramID;
import musin.seeker.instagram.api.InstagramIdFactory;
import musin.seeker.instagram.db.InstagramSeekerService;
import org.springframework.stereotype.Component;

@Component
public class InstagramServiceCommands extends BasicServiceCommands<InstagramID> {
  public InstagramServiceCommands(InstagramSeekerService seekerService, InstagramIdFactory idFactory) {
    super("instagram", seekerService, idFactory);
  }
}
