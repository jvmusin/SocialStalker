package musin.socialstalker.telegram.bot;

import lombok.Data;
import musin.socialstalker.db.model.Stalker;

@Data
public class Session {
  boolean done;
  private Stalker stalker;
  private String command;
  private String service;

  public Session(Stalker stalker) {
    this.stalker = stalker;
  }
}
