package musin.socialstalker.updater;

import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.RelationType;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService<ID, TRelationType extends RelationType> {
  List<NotifiableUpdate<RelationType>> saveAll(List<Update> updates, ID target);

  CompletableFuture<RelationList<RelationType>> buildList(ID target);
}
