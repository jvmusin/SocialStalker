package musin.seeker.updater;

import lombok.RequiredArgsConstructor;
import musin.seeker.relation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Stream.empty;

@RequiredArgsConstructor
public abstract class RelationListPullerBase<
    ID,
    TUser extends User<ID>,
    TRelationType,
    TRelation extends Relation<TUser, TRelationType>,
    TUpdate extends Update<TUser, TRelationType>,
    TRelationList extends RelationList<TUser, TRelationType, TRelation, ?>>
    implements RelationListPuller<ID, TRelationList> {

  private final RelationListFactory<TRelationList> relationListFactory;
  private final UpdateFactory<TUser, TRelationType, TUpdate> updateFactory;
  private final RelationFactory<ID, TUser, TRelationType, TRelation> relationFactory;

  @SafeVarargs
  public final CompletableFuture<TRelationList> combine(CompletableFuture<Stream<TRelation>>... lists) {
    return Arrays.stream(lists)
        .reduce(completedFuture(empty()), (a, b) -> a.thenCombine(b, Stream::concat))
        .thenApply(relations -> {
          TRelationList list = relationListFactory.create();
          relations.forEach(r -> list.apply(updateFactory.create(r.getUser(), null, r.getType())));
          return list;
        });
  }

  protected CompletableFuture<Stream<TRelation>> load(Supplier<CompletableFuture<List<ID>>> query, TRelationType type) {
    return query.get().thenApply(s -> s.stream().map(id -> relationFactory.create(id, type)));
  }
}
