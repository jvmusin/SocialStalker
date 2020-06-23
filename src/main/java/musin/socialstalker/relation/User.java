package musin.socialstalker.relation;

public interface User<ID> {
  ID getId();

  //should start with "id:" (ex. "123: name (nickname)")
  String getFullyQualifiedName();

  String getLink();

  default String getMarkdownLink() {
    return String.format("[%s](%s)", getFullyQualifiedName(), getLink());
  }
}
