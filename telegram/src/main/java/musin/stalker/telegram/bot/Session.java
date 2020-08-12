package musin.stalker.telegram.bot;

import lombok.Data;
import musin.stalker.db.model.Stalker;

@Data
public class Session {
  private boolean done;
  private Stalker stalker;
  private String command;
  private String service;

  public Session(Stalker stalker) {
    this.stalker = stalker;
  }
}
