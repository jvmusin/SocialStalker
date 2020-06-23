package musin.socialstalker.updater;

import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService<ID, TRelationType> {

  List<? extends NotifiableUpdate<TRelationType>> saveAll(List<? extends Update<?>> updates, ID target);

  CompletableFuture<? extends RelationList<TRelationType>> buildList(ID target);
}
