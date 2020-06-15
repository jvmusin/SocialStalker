package musin.seeker.instagram.api;

import lombok.Data;

@Data
public class InstagramID {
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
