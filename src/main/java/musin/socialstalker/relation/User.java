package musin.socialstalker.relation;

public interface User<ID> {
  ID getId();

  String getFullyQualifiedName();

  String getLink();

  default String getMarkdownLink() {
    return String.format("[%s](%s)", getFullyQualifiedName(), getLink());
  }
}
