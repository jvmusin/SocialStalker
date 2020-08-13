package musin.stalker.updater;

import musin.stalker.api.Id;
import musin.stalker.db.model.Stalker;
import musin.stalker.notifier.NotifiableUpdate;
import musin.stalker.relation.Update;
import musin.stalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GeneralUpdateService {

  List<NotifiableUpdate> saveAll(Stalker stalker, List<Update> updates, Id target);

  void removeAllByTarget(Stalker stalker, Id target);

  CompletableFuture<RelationList> buildList(Stalker stalker, Id target);
}
