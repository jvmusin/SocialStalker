package musin.socialstalker.relation.list;

import musin.socialstalker.relation.RelationType;

public interface RelationListFactory<TRelationType extends RelationType> {
  RelationList<RelationType> create();
}
