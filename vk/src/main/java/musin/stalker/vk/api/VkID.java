package musin.stalker.vk.api;

import lombok.Data;
import musin.stalker.db.Id;

@Data
public class VkID implements Id {
  private final Integer value;

  public VkID(Integer value) {
    this.value = value;
  }

  public VkID(String s) {
    this.value = Integer.parseInt(s);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
