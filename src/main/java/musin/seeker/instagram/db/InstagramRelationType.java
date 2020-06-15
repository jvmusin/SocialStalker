package musin.seeker.instagram.db;

public enum InstagramRelationType {
  FOLLOWER,
  FOLLOWING;

  public static InstagramRelationType parseNullSafe(String name) {
    return name == null ? null : InstagramRelationType.valueOf(name);
  }
}
