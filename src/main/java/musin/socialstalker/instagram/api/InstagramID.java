package musin.socialstalker.instagram.api;

import lombok.Data;
import musin.socialstalker.api.Id;

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
