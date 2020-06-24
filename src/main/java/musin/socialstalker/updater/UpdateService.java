package musin.socialstalker.updater;

import musin.socialstalker.api.Id;
import musin.socialstalker.notifier.NotifiableUpdate;
import musin.socialstalker.relation.Update;
import musin.socialstalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService {
  List<NotifiableUpdate> saveAll(List<Update> updates, Id target);

  CompletableFuture<RelationList> buildList(Id target);
}
