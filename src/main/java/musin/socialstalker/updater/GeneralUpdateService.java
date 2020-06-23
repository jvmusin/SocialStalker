package musin.socialstalker.updater;

import musin.socialstalker.db.model.Stalker;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GeneralUpdateService<ID, TRelationType extends RelationType> {

  List<NotifiableUpdate<RelationType>> saveAll(Stalker stalker, List<Update> updates, ID target);

  void removeAllByTarget(Stalker stalker, ID target);

  CompletableFuture<RelationList<RelationType>> buildList(Stalker stalker, ID target);
}
