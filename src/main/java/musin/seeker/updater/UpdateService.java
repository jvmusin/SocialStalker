package musin.seeker.updater;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UpdateService<
    ID,
    TUpdate,
    TRelationList,
    TNotifiableUpdate> {

  List<TNotifiableUpdate> saveAll(List<? extends TUpdate> updates, ID target);

  void removeAllByTarget(ID target);

  CompletableFuture<TRelationList> buildList(ID target);
}
