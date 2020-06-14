package musin.seeker.vk.relation;

public enum VkRelationType {
  FRIEND,
  FOLLOWER;

  public static VkRelationType parseNullSafe(String name) {
    return name == null ? null : valueOf(name);
  }
}
