package musin.seeker.db;

public interface IdFactory<ID> {
  ID parse(String id);
}
