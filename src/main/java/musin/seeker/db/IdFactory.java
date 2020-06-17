package musin.seeker.db;

public interface IdFactory<ID> {
  ID create(String id);
}
