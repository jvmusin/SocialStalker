package musin.socialstalker.updater;

import lombok.RequiredArgsConstructor;
import musin.socialstalker.relation.*;
import musin.socialstalker.relation.list.RelationList;
import musin.socialstalker.relation.list.RelationListFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Stream.empty;

@RequiredArgsConstructor
public abstract class RelationListPullerBase<ID> implements RelationListPuller<ID> {

  private final RelationListFactory relationListFactory;
  private final UpdateFactory updateFactory;
  private final RelationFactory relationFactory;
  private final UserFactory<ID> userFactory;
  private final List<Query> queries = new ArrayList<>();

  protected void registerQuery(Function<ID, CompletableFuture<List<ID>>> query, RelationType resultType) {
    queries.add(new Query(query, resultType));
  }

  @Override
  public CompletableFuture<RelationList> pull(ID userId) {
    return queries.stream()
        .map(q -> q.call(userId))
        .reduce(completedFuture(empty()), (a, b) -> a.thenCombine(b, Stream::concat))
        .thenApply(relations -> {
          RelationList list = relationListFactory.create();
          relations.forEach(r -> list.apply(updateFactory.creating(r.getUser(), r.getType())));
          return list;
        });
  }

  @RequiredArgsConstructor
  private class Query {
    final Function<ID, CompletableFuture<List<ID>>> query;
    final RelationType type;

    CompletableFuture<Stream<Relation>> call(ID userId) {
      return query.apply(userId).thenApply(
          list -> list.stream().map(
              id -> relationFactory.create(userFactory.create(id), type)
          )
      );
    }
  }
}
