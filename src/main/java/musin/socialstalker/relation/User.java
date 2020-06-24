package musin.socialstalker.relation;

import musin.socialstalker.api.Id;

public interface User<ID extends Id> {
  ID getId();

  //should start with "id:" (ex. "123: name (nickname)")
  String getFullyQualifiedName();

  String getLink();

  default String getMarkdownLink() {
    return String.format("[%s](%s)", getFullyQualifiedName(), getLink());
  }
}
