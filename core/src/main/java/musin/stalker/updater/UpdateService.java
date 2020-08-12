package musin.stalker.updater;

import musin.stalker.api.Id;
import musin.stalker.notifier.NotifiableUpdate;
import musin.stalker.relation.Update;
import musin.stalker.relation.list.RelationList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService {
  List<NotifiableUpdate> saveAll(List<Update> updates, Id target);

  CompletableFuture<RelationList> buildList(Id target);
}
