package musin.seeker.instagram.db;

import musin.seeker.instagram.notifier.InstagramNotifiableUpdate;
import musin.seeker.instagram.relation.InstagramID;
import musin.seeker.instagram.relation.InstagramRelationList;
import musin.seeker.instagram.relation.InstagramUpdate;
import musin.seeker.updater.UpdateService;

import java.util.concurrent.CompletableFuture;

public interface InstagramUpdateService extends UpdateService<InstagramID, InstagramUpdate, InstagramRelationList, InstagramNotifiableUpdate> {
  default CompletableFuture<InstagramRelationList> buildList(InstagramID owner) {
    return findAllByOwner(owner).thenApply(InstagramRelationList::new);
  }
}
