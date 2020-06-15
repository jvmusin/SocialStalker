package musin.seeker.instagram.relation;

public enum InstagramRelationType {
  FOLLOWER,
  FOLLOWING;

  public static InstagramRelationType parseNullSafe(String name) {
    return name == null ? null : InstagramRelationType.valueOf(name);
  }
}
