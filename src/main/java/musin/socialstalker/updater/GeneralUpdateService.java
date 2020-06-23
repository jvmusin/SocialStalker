package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GeneralUpdateService<ID> {

  List<NotifiableUpdate> saveAll(Stalker stalker, List<Update> updates, ID target);

  void removeAllByTarget(Stalker stalker, ID target);

  CompletableFuture<RelationList> buildList(Stalker stalker, ID target);
}
