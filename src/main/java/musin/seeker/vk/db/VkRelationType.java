package musin.seeker.vk.db;

public enum VkRelationType {
  FRIEND,
  FOLLOWER;

  public static VkRelationType parseNullSafe(String name) {
    return name == null ? null : valueOf(name);
  }
}
