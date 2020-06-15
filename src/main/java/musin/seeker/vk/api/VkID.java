package musin.seeker.vk.api;

import lombok.Data;

@Data
public class VkID {
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
