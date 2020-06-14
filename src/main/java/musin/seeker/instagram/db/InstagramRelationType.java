package musin.seeker.instagram.db;

public enum InstagramRelationType {
  FOLLOWER;

  public static InstagramRelationType parseNullSafe(String name) {
    return name == null ? null : InstagramRelationType.valueOf(name);
  }
}
