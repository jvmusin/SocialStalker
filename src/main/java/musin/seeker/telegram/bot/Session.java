package musin.seeker.telegram.bot;

import lombok.Data;
import musin.seeker.db.model.Stalker;

@Data
public class Session {
  boolean done;
  private Stalker stalker;//with user id or chat id or something
  private String command;
  private String service;

  public Session(Stalker stalker) {
    this.stalker = stalker;
  }
}
