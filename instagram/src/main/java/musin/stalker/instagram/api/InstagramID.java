package musin.stalker.instagram.api;

import lombok.Data;
import musin.stalker.db.Id;

@Data
public class InstagramID implements Id {
  private final Long value;

  public InstagramID(Long value) {
    this.value = value;
  }

  public InstagramID(String s) {
    this.value = Long.parseLong(s);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
