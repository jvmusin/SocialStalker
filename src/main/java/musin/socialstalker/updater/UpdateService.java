package musin.socialstalker.updater;

import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService<
    ID,
    TUpdate extends Update<?, ?>,
    TRelationList extends RelationList<?, ?>,
    TNotifiableUpdate extends NotifiableUpdate<?, ?>> {

  List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID target);

  void removeAllByTarget(ID target);

  CompletableFuture<TRelationList> buildList(ID target);
}
