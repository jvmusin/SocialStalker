package musin.socialstalker.relation;

public interface Update {
  User getSuspected();

  RelationType getWas();

  RelationType getNow();
}
