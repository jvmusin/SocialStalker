package musin.socialstalker.updater;

import musin.socialstalker.api.Id;
import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GeneralUpdateService {

  List<NotifiableUpdate> saveAll(Stalker stalker, List<Update> updates, Id target);

  void removeAllByTarget(Stalker stalker, Id target);

  CompletableFuture<RelationList> buildList(Stalker stalker, Id target);
}
