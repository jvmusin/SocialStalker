package musin.seeker.instagram.db;

import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramUpdate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface InstagramRelationUpdateService {

  CompletableFuture<List<InstagramNotifiableUpdate>> findAllByOwner(long owner);

  default CompletableFuture<InstagramRelationList> buildList(long owner) {
    return findAllByOwner(owner).thenApply(InstagramRelationList::new);
  }

  List<InstagramNotifiableUpdate> saveAll(List<? extends InstagramUpdate> updates, long owner);
}
