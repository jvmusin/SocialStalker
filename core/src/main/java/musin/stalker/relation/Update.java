package musin.stalker.relation;

public interface Update {
  User getSuspected();

  RelationType getWas();

  RelationType getNow();
}
