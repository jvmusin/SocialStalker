package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GeneralUpdateService<
    ID,
    TUpdate extends Update<?>,
    TRelationList extends RelationList<?>,
    TNotifiableUpdate extends NotifiableUpdate<?>> {

  List<TNotifiableUpdate> saveAll(Stalker stalker, List<? extends Update<?>> updates, ID target);

  void removeAllByTarget(Stalker stalker, ID target);

  CompletableFuture<TRelationList> buildList(Stalker stalker, ID target);
}
