package musin.seeker.updater;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService<
    ID,
    TUpdate,
    TRelationList,
    TNotifiableUpdate> {

  CompletableFuture<List<TNotifiableUpdate>> findAllByOwner(ID owner);

  List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID owner);

  CompletableFuture<TRelationList> buildList(ID owner);
}
