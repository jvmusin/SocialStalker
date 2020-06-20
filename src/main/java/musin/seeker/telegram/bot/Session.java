package musin.seeker.telegram.bot;

import lombok.Data;

@Data
public class Session {
  boolean done;
  private Integer userId;
  private String command;
  private String service;

  public Session(Integer userId) {
    this.userId = userId;
  }
}
