package musin.socialstalker.relation.list;

public interface RelationListFactory<TRelationType> {
  RelationList<TRelationType> create();
}
