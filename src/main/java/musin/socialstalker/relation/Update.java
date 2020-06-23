package musin.socialstalker.relation;

public interface Update<TRelationType> {
  User<?> getSuspected();

  TRelationType getWas();

  TRelationType getNow();
}
