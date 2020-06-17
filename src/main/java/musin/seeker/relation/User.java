package musin.seeker.relation;

public interface User<ID> {
  ID getId();

  String getFullyQualifiedName();

  String getLink();
}
